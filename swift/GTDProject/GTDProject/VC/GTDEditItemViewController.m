//
//  GTDEditItemViewController.m
//  GTDProject
//
//  Created by fang on 2021/12/11.
//

#import "GTDEditItemViewController.h"
#import "GTDViewModel.h"

@interface GTDEditItemViewController ()

@property (nonatomic, strong) UITextField *editItemTF;

@end

@implementation GTDEditItemViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    [self initView];
}

- (void)initView {
    self.title = @"edit item";
    self.view.backgroundColor = UIColor.whiteColor;
    
    [self.view addSubview:self.editItemTF];
}

// MARK: - Getter
- (UITextField *)editItemTF {
    if (!_editItemTF) {
        UITextField *tf = [[UITextField alloc] initWithFrame:CGRectMake(20, 84, CGRectGetWidth(self.view.frame) - 20, 40)];
        tf.font = [UIFont systemFontOfSize:14];
        tf.borderStyle = UITextBorderStyleRoundedRect;
        tf.text = self.viewModel.editItem.title;
        tf.returnKeyType = UIReturnKeyGo;

        _editItemTF = tf;
    }
    
    return _editItemTF;
}

- (void)viewDidDisappear:(BOOL)animated {
    NSString *str = self.editItemTF.text;
    
    [self.viewModel updateItemTitle:str item:self.viewModel.editItem];
}

@end
