//
//  GTDItem.h
//  GTDProject
//
//  Created by fang on 2021/12/6.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface GTDItem : NSObject

@property (nonatomic, copy) NSString *title;
@property (nonatomic, assign) BOOL completed;

- (instancetype)initWithTitle:(NSString *)title isCompleted:(BOOL)completed;

@end

NS_ASSUME_NONNULL_END
