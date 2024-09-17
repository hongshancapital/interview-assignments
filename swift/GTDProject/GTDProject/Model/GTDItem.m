//
//  GTDItem.m
//  GTDProject
//
//  Created by fang on 2021/12/6.
//

#import "GTDItem.h"

@interface GTDItem ()

@end

@implementation GTDItem

- (instancetype)initWithTitle:(NSString *)title isCompleted:(BOOL)completed {
    self = [super init];
    
    if (self) {
        _title = title.copy;
        _completed = completed;
    }
    
    return self;
}

@end
