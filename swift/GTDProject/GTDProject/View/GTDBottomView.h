//
//  GTDBottomView.h
//  GTDProject
//
//  Created by fang on 2021/12/11.
//

#import <UIKit/UIKit.h>
#import "GTDViewModel.h"

NS_ASSUME_NONNULL_BEGIN

typedef NS_ENUM(NSUInteger, GTDBottomViewMode) {
    GTDBottomViewModeDefault,
    GTDBottomViewModeEdit,
};

@interface GTDBottomView : UIView

@property (nonatomic, strong) GTDViewModel *viewModel;

@property (nonatomic, assign) GTDBottomViewMode mode;

@property (nonatomic, strong) UITextField *addTodoTF;

- (void)updateSelectedGroup:(GTDGroup *)group;

@end

NS_ASSUME_NONNULL_END
