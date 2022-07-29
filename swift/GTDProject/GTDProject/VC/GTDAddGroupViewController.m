//
//  GTDAddItemViewController.m
//  GTDProject
//
//  Created by fang on 2021/12/10.
//

#import "GTDAddGroupViewController.h"
#import "GTDViewModel.h"

@interface GTDAddGroupViewController ()

@property (nonatomic, strong) UITextField *addGroupTF;

@property (nonatomic, strong) UIButton *addGroupBtn;

@end

@implementation GTDAddGroupViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    
    [self initView];
    
}

// MARK: - Private

- (void)initView {
    self.title = @"add group";
    self.view.backgroundColor = UIColor.whiteColor;
    
    [self.view addSubview:self.addGroupTF];
    [self.view addSubview:self.addGroupBtn];
}

- (void)addGroupBtnClick {
    if (!self.addGroupTF.text.length) {
        return;
    }
    
    NSString *str = self.addGroupTF.text;
    
    [self.viewModel addGTDGroup:str];
}

// MARK: - Getter
- (UITextField *)addGroupTF {
    if (!_addGroupTF) {
        UITextField *tf = [[UITextField alloc] initWithFrame:CGRectMake(20, 84, CGRectGetWidth(self.view.frame) - 120, 40)];
        tf.font = [UIFont systemFontOfSize:14];
        tf.borderStyle = UITextBorderStyleRoundedRect;
        tf.placeholder = @"input new group name";
        tf.returnKeyType = UIReturnKeyGo;

        _addGroupTF = tf;
    }
    
    return _addGroupTF;
}

- (UIButton *)addGroupBtn {
    if (!_addGroupBtn) {
        UIButton *btn = [UIButton buttonWithType:UIButtonTypeSystem];
        btn.frame = CGRectMake(CGRectGetWidth(self.view.frame) - 80, 84, 70, 40);
        [btn setTitle:@"addGroup" forState:UIControlStateNormal];
        [btn addTarget:self action:@selector(addGroupBtnClick) forControlEvents:UIControlEventTouchUpInside];
        
        _addGroupBtn = btn;
    }
    
    return _addGroupBtn;
}
@end
