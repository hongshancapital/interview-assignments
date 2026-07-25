//
//  GTDPopupView.h
//  GTDProject
//
//  Created by fang on 2021/12/11.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface GTDPopupView : UIView

+ (instancetype)popUpContentView:(UIView *)contentView
                          onView:(UIView *)onView;

+ (void)hidenPopView;

@end

NS_ASSUME_NONNULL_END
