//
//  DayCounter.h
//  ZarkALot
//
//  Created by Dan Craft on 2/21/12.
//  Copyright (c) 2012 955 Dreams. All rights reserved.
//

/*
 A day counter counts up by days from some epoch.  (An epoch is the beginning of a distinctive period of time.)  This can be used by apps that present new content on a daily basis to determine which day's content to present.  It handles the time zone issues that otherwise plague such calculations (e.g. March 3rd, 10pm in California is March 4th, 6am in England).
 
 For example, an app whose first daily content was for 3/4/2012 (March 4th) would set the epoch to 3/4/2012.  3/4/2012 would then be day 0 (or day 1 if the origin were set to 1).  3/9/2012 would be day 5 (or day 6 if the origin were set to 1).  Etc.
 
 Callers can KVO the dayIndex to be notified when the day ticks over.  That will occur at midnight, local time, as long as the app is in the foreground.  If the app is in the background at midnight, then the notification will occur when the app is next brought into the foreground.
 
 Sample usage, e.g. from the app delegate:
     -(BOOL) application:(UIApplication*)application 
                 didFinishLaunchingWithOptions:(NSDictionary*)launchOptions {
         ...
         self.dayCounter = [[[NFFDayCounter alloc] init] autorelease];
         self.dayCounter.epoch = [NFFDayCounter utcForGregorianYear:2012 month:3 day:4];
         [self.dayCounter addObserver:self forKeyPath:@"dayIndex" 
                 options:NSKeyValueObservingOptionInitial context:nil];
         ...
     }
 
     -(void) observeValueForKeyPath:(NSString*)keyPath ofObject:(id)object 
             change:(NSDictionary*)change context:(void*)context {
         if ([keyPath isEqualToString:@"dayIndex"]) {
             // display something appropriate for the new day
         }
     }
 */

#import <Foundation/Foundation.h>


@interface NFFDayCounter : NSObject

@property (nonatomic, retain) NSDate* epoch; // midnight UTC for the desired year, month, and day (other components ignored)

@property (nonatomic) NSInteger origin; // 0 or 1; 0 default


@property (nonatomic, readonly) NSInteger dayIndex;


+(NSDate*) utcForGregorianYear:(NSUInteger)year month:(NSUInteger)month day:(NSUInteger)day; // convenience method

// for development only: this property indicates the next time at which the dayIndex will be calculated and set, causing any KVO notifications; it is normally midnight local time but can be set explicitly for testing purposes; note this does not cause the day to increment (unless set to midnight), but just causes KVO notification; this should be used only for development--no production code should reference it
@property (nonatomic, retain) NSDate* nextAutoAdvanceTime;

@end
