//
//  GTDPopupListView.h
//  GTDProject
//
//  Created by fang on 2021/12/11.
//

#import <UIKit/UIKit.h>
#import "GTDViewModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface GTDPopupListView : UIView

- (instancetype)initWithViewModel:(GTDViewModel *)viewModel;

@property (nonatomic, copy) void(^dismissHandler)(void);

@end

NS_ASSUME_NONNULL_END
