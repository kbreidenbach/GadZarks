//
//  DayCounter.m
//  Zark-A-Lot
//
//  Created by Dan Craft on 2/21/12.
//  Copyright (c) 2012 955 Dreams. All rights reserved.
//

#import "NFFDayCounter.h"


#pragma mark -

@interface NFFDayCounter ()

@property (nonatomic, readwrite) NSInteger dayIndex;
@property (nonatomic, retain) NSTimer* autoAdvanceTimer;

-(void) activateAutoAdvance;
-(void) bindObserversForAutoAdvance;
-(void) cancelAutoAdvanceTimer;
-(void) cycleAutoAdvance;
-(void) deactivateAutoAdvance;
+(NSDate*) midnightTonight;
-(void) noteNextAutoAdvanceTime;
-(void) unbindObserversForAutoAdvance;
-(void) updateDayIndex;

@end


#pragma mark -

@implementation NFFDayCounter


#pragma mark - UTC Dates for Epoch

+(NSCalendar*) utcCalendar {
    static NSCalendar* utcCalendar = nil;
    if (! utcCalendar) {
        utcCalendar = [[NSCalendar alloc] initWithCalendarIdentifier:NSGregorianCalendar];
        utcCalendar.locale = [[[NSLocale alloc] initWithLocaleIdentifier:@"UTC"] autorelease];
        utcCalendar.timeZone = [NSTimeZone timeZoneForSecondsFromGMT:0];
    }
    return utcCalendar;
}

+(NSDate*) utcForGregorianYear:(NSUInteger)year month:(NSUInteger)month day:(NSUInteger)day {
    NSDateComponents* dayComponents = [[[NSDateComponents alloc] init] autorelease];
    dayComponents.year = year;
    dayComponents.month = month;
    dayComponents.day = day;
    return [[NFFDayCounter utcCalendar] dateFromComponents:dayComponents];
}

+(NSDate*) dayUTCForDate:(NSDate*)date {
    NSDateComponents* dayComponents = [[NSCalendar currentCalendar] components:(NSYearCalendarUnit | NSMonthCalendarUnit | NSDayCalendarUnit) fromDate:date];
    return [[NFFDayCounter utcCalendar] dateFromComponents:dayComponents];
}


#pragma mark - Life Cycle & Setup

-(id) init {
    if (self = [super init]) {
        self.epoch = [NSDate date];
        [self updateDayIndex];
        [self activateAutoAdvance];
    }
    return self;
}

-(void) dealloc {
    [self deactivateAutoAdvance];
    self.autoAdvanceTimer = nil;
    self.epoch = nil;
    self.nextAutoAdvanceTime = nil;
    [super dealloc];
}

-(void) activateAutoAdvance {
    [self bindObserversForAutoAdvance];
    [self noteNextAutoAdvanceTime];
    //    [self cycleAutoAdvance];
}

-(void) deactivateAutoAdvance {
    [self cancelAutoAdvanceTimer];
    [self unbindObserversForAutoAdvance];
}

-(void) bindObserversForAutoAdvance {
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(cancelAutoAdvanceTimer) name:UIApplicationWillResignActiveNotification object:nil];
    // on return from sleep/lock, consider auto advance
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(cycleAutoAdvance) name:UIApplicationDidBecomeActiveNotification object:nil];
}

-(void) unbindObserversForAutoAdvance {
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIApplicationWillResignActiveNotification object:nil];
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIApplicationDidBecomeActiveNotification object:nil];
}

+(NSString*) tmp_formattedDate:(NSDate*)date {
    static NSDateFormatter* formatter = nil;
    if (! formatter) {
        formatter = [[NSDateFormatter alloc] init];
        formatter.dateFormat = @"MMM dd hh:mm:ss a";
    }
    return [formatter stringFromDate:date];
}


#pragma mark - Auto-Advance

-(void) updateDayIndex {
    // epoch is in UTC, as is the calculation against it to determine the day index
    NSDate* todayUTC = [NFFDayCounter dayUTCForDate:[NSDate date]];
    self.dayIndex = floor([todayUTC timeIntervalSinceDate:self.epoch] / (24*60*60)) + self.origin;
}


// This is the main calendar auto-advance cycle.  It will auto-advance, if appropriate, and set a timer for the next cycle.
// (Next auto-advance time must be set upon entry.) If it's time for auto-advance, do the advance.  Determine next auto-advance time.  Set timer to fire off next cycle.
// This has to be robust.  It can be executed by a timer firing, by an app life cycle notification, or by the nextAutoAdvanceTime being manually set by a client.  Timers are not that accurate (+/- a second or so): if they fire early then the action could end up being executed multiple times--just inefficient if idempotent, but this code avoids it.
-(void) cycleAutoAdvance {
    NSAssert(self.nextAutoAdvanceTime, @"NFFDayCounter's nextAutoAdvanceTime must be non-nil when cycleAutoAdvance is called");
    [self.autoAdvanceTimer invalidate]; // OK even if already fired
    if ([self.nextAutoAdvanceTime timeIntervalSinceNow] < 0) {
        [self updateDayIndex];
        [self noteNextAutoAdvanceTime]; // this invokes the next cycle, which will execute the else branch below
//        self.nextAutoAdvanceTime = [self midnightTonight]; // this assignment invokes the next cycle, which will execute the else branch
    } else {
        NSTimeInterval intervalToAutoAdvance = [self.nextAutoAdvanceTime timeIntervalSinceNow];  // if interval <= 0 (from timer inaccuracies), timer will fire in .1 ms
        self.autoAdvanceTimer = [NSTimer scheduledTimerWithTimeInterval:intervalToAutoAdvance target:self selector:@selector(cycleAutoAdvance) userInfo:nil repeats:NO];
    }
}

-(void) cancelAutoAdvanceTimer { // OK even if timer already fired
    if ([self.autoAdvanceTimer isValid]) {
        [self.autoAdvanceTimer invalidate];
    }
    self.autoAdvanceTimer = nil;
}

-(void) noteNextAutoAdvanceTime {
    if ([self.nextAutoAdvanceTime timeIntervalSinceNow] <= 0) // incl initial nil
        self.nextAutoAdvanceTime = [NFFDayCounter midnightTonight];
}

+(NSDate*) midnightTonight {
    NSDateComponents* todayYearMonthDay = [[NSCalendar currentCalendar] components:NSYearCalendarUnit|NSMonthCalendarUnit|NSDayCalendarUnit fromDate:[NSDate date]];
    NSDate* midnightLastNight = [[NSCalendar currentCalendar] dateFromComponents:todayYearMonthDay];
    NSDate* midnightTonight = [NSDate dateWithTimeInterval:24*60*60 sinceDate:midnightLastNight];
    return midnightTonight;
}


#pragma mark - Properties

@synthesize autoAdvanceTimer;
@synthesize dayIndex;
@synthesize epoch;
@synthesize origin;
@synthesize nextAutoAdvanceTime;

-(void) setNextAutoAdvanceTime:(NSDate *)theNextAutoAdvanceTime {
    NSAssert(theNextAutoAdvanceTime, @"NFFDayCounter nextAutoAdvanceTime cannot be nil");
    [theNextAutoAdvanceTime retain];
    [nextAutoAdvanceTime release];
    nextAutoAdvanceTime = theNextAutoAdvanceTime;
    [self cycleAutoAdvance];
}

-(void)setEpoch:(NSDate *)epochUTC {
    NSAssert(epochUTC, @"NFFDayCounter epoch cannot be nil");
    NSDate *old = epoch;
    epoch = [[NFFDayCounter dayUTCForDate:epochUTC] retain];
    [old release];
    [self updateDayIndex];
}

-(void)setOrigin:(NSInteger)theOrigin {
    origin = theOrigin == 1 ? 1 : 0;
    [self updateDayIndex];
}

@end
