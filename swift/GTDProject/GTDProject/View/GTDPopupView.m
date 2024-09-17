//
//  GTDPopupView.m
//  GTDProject
//
//  Created by fang on 2021/12/11.
//

#import "GTDPopupView.h"

@interface GTDPopupView ()

@property (nonatomic ,strong) UIView *popContainerView;
@property (nonatomic ,weak) UIView *contentView;
@property (nonatomic ,weak) UIView *onView;
@property (nonatomic ,strong) UIResponder *backCtl;

@end

@implementation GTDPopupView

static  NSInteger const popViewTag  = 100;

+ (instancetype)popUpContentView:(UIView *)contentView
                        onView:(UIView *)onView {
    UIWindow *window = [GTDPopupView getWindow];
    GTDPopupView *newPopView = [[GTDPopupView alloc] initWithFrame:window.bounds onView:onView contentView:contentView];
    newPopView.tag = popViewTag;
    [window addSubview:newPopView];
    
    [newPopView setPopMenuSubViewFrame];
    
    [newPopView bringSubviewToFront:newPopView.popContainerView];
    return newPopView;
}

- (instancetype)initWithFrame:(CGRect)frame
                       onView:(UIView *)onView
                  contentView:(UIView *)contentView {
    self = [super initWithFrame:frame];
    if (self) {
        self.contentView = contentView;
        self.onView = onView;

        UIControl *backCtl = [[UIControl alloc] initWithFrame:self.bounds];
        [self addSubview:backCtl];
        self.backCtl = backCtl;
        [backCtl addTarget:self action:@selector(backClick) forControlEvents:UIControlEventTouchUpInside];
    }
    return self;
}

+ (GTDPopupView *)getCurrentPopView{
    UIWindow *window = [GTDPopupView getWindow];
    
    GTDPopupView *popView = (GTDPopupView *)[window viewWithTag:popViewTag];
    return popView;
}

+ (void)hidenPopView {
    GTDPopupView *popView = [self getCurrentPopView];
    [popView removeFromSuperview];
}

// MARK: - Private
- (void)setPopMenuSubViewFrame{
    CGRect contentFrame = self.contentView.bounds;
    CGRect onViewFrame = [self.onView convertRect:self.onView.bounds toView:nil];
    
    UIWindow *window = [GTDPopupView getWindow];
    
    //1.2、计算内容在window的位置
    contentFrame.origin.y = CGRectGetMaxY(onViewFrame) - contentFrame.size.height;
    contentFrame.origin.x = onViewFrame.origin.x + onViewFrame.size.width/2 - contentFrame.size.width/2;
    
    self.popContainerView.frame = contentFrame;
    
    //3、计算内容实际的位置
    self.contentView.frame = contentFrame;
    [window addSubview:self.contentView];
    self.contentView.frame = [self.contentView convertRect:self.contentView.bounds toView:self.popContainerView];
    [self.popContainerView addSubview:self.contentView];
}

- (void)backClick{
    [GTDPopupView hidenPopView];
}

// MARK: - Getter && Setter

- (UIView *)popContainerView{
    if (_popContainerView == nil) {
        _popContainerView = [[UIView alloc] init];
        [self addSubview:_popContainerView];
    }
    return _popContainerView;
}

+ (UIWindow *)getWindow {
    return [UIApplication sharedApplication].windows.lastObject;;
}
@end
