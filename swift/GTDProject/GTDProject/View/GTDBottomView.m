//
//  GTDBottomView.m
//  GTDProject
//
//  Created by fang on 2021/12/11.
//

#import "GTDBottomView.h"
#import "GTDPopupListView.h"
#import "GTDPopupView.h"

@interface GTDBottomView ()<UITextFieldDelegate>

@property (nonatomic, strong) UIButton *selectGroupBtn;

@end

@implementation GTDBottomView

- (instancetype)initWithFrame:(CGRect)frame
{
    self = [super initWithFrame:frame];
    if (self) {
        [self addSubview:self.addTodoTF];
    }
    return self;
}

// MARK: - Private
- (void)selectGroupBtnClick:(UIButton *)sender {
    GTDPopupListView *view = [[GTDPopupListView alloc] initWithViewModel:self.viewModel];
    view.backgroundColor = UIColor.grayColor;
    view.layer.cornerRadius = 5;
    view.dismissHandler = ^{
        [GTDPopupView hidenPopView];
    };
    
    GTDPopupView *popView = [GTDPopupView popUpContentView:view onView:sender];
    popView.backgroundColor = [[UIColor blackColor] colorWithAlphaComponent:0.3];
}

- (void)updateSelectedGroup:(GTDGroup *)group {
    [self.selectGroupBtn setTitle:group.groupName forState:UIControlStateNormal];
}

// MARK: - Getter
- (UITextField *)addTodoTF {
    if (!_addTodoTF) {
        UITextField *tf = [[UITextField alloc] initWithFrame:CGRectMake(20, 0, self.frame.size.width - 40,self.frame.size.height - 10)];
        tf.font = [UIFont systemFontOfSize:14];
        tf.borderStyle = UITextBorderStyleRoundedRect;
        tf.placeholder = @"add new...";
        tf.returnKeyType = UIReturnKeyGo;
        tf.delegate = self;

        _addTodoTF = tf;
    }
    
    return _addTodoTF;
}

- (UIButton *)selectGroupBtn {
    if (!_selectGroupBtn) {
        UIButton *btn = [UIButton buttonWithType:UIButtonTypeSystem];
        btn.backgroundColor = UIColor.whiteColor;
        [btn setTitleColor:UIColor.blackColor forState:UIControlStateNormal];
        [btn setTitle:self.viewModel.selectedGroup.groupName forState:UIControlStateNormal];
        [btn addTarget:self action:@selector(selectGroupBtnClick:) forControlEvents:UIControlEventTouchUpInside];
        btn.titleLabel.font = [UIFont systemFontOfSize:8];
        btn.layer.masksToBounds = YES;
        btn.layer.cornerRadius = 10;
        
        _selectGroupBtn = btn;
    }
    
    return _selectGroupBtn;
}

- (void)setMode:(GTDBottomViewMode)mode {
    if (mode == _mode) {
        return;
    }
    
    if (mode == GTDBottomViewModeEdit) {
        [self addSubview:self.selectGroupBtn];
        
        [UIView animateWithDuration:0.2 animations:^{
            self.selectGroupBtn.frame = CGRectMake(CGRectGetWidth(self.bounds) - 120, 5, 100, CGRectGetHeight(self.addTodoTF.bounds) - 10);
            self.addTodoTF.frame = CGRectMake(20, 0, self.frame.size.width - 140,self.frame.size.height - 10);
        }];
        
    } else {
        [UIView animateWithDuration:0.2 animations:^{
            [self.selectGroupBtn removeFromSuperview];
            
            self.addTodoTF.frame = CGRectMake(20, 0, self.frame.size.width - 40,self.frame.size.height - 10);
        }];
    }
    
    _mode = mode;
}

@end
