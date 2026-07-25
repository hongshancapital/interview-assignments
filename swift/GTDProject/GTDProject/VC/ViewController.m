//
//  ViewController.m
//  GTDProject
//
//  Created by fang on 2021/12/6.
//

#import "ViewController.h"
#import "GTDItemCell.h"
#import "GTDBottomView.h"
#import "GTDViewModel.h"
#import "GTDAddGroupViewController.h"
#import "GTDEditItemViewController.h"

@interface ViewController ()<UITableViewDelegate,UITableViewDataSource,UISearchBarDelegate,UITextFieldDelegate>

@property (nonatomic, strong) UITableView *tableView;

@property (nonatomic, strong) GTDViewModel *viewModel;

@property (nonatomic, strong) GTDBottomView *bottomView;

@property (nonatomic, assign) CGFloat keyBoardHeight;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.title = @"List";
    
    self.viewModel = [[GTDViewModel alloc] initWithGroups:@[
        [[GTDGroup alloc] initWithName:@"SwiftUI Essentials" items:@[
            [[GTDItem alloc] initWithTitle:@"Building Lists and Navigation" isCompleted:NO],
            [[GTDItem alloc] initWithTitle:@"Creating and Combining Views" isCompleted:YES],
            [[GTDItem alloc] initWithTitle:@"Handing User Input" isCompleted:YES],
        ]],
        [[GTDGroup alloc] initWithName:@"Drawing and Animation" items:@[
            [[GTDItem alloc] initWithTitle:@"Animating Views and Transitions" isCompleted:NO],
            [[GTDItem alloc] initWithTitle:@"Drawing Paths and Shapes" isCompleted:YES],
        ]],
        [[GTDGroup alloc] initWithName:@"App Design and Layout" items:@[
            [[GTDItem alloc] initWithTitle:@"Composing Complex Interfaces" isCompleted:NO],
            [[GTDItem alloc] initWithTitle:@"Working with UI Controls" isCompleted:NO],
        ]],
        [[GTDGroup alloc] initWithName:@"Framework Integration" items:@[
            [[GTDItem alloc] initWithTitle:@"Interfacing with UIKit" isCompleted:NO],
            [[GTDItem alloc] initWithTitle:@"Creating a watchOS App" isCompleted:NO],
            [[GTDItem alloc] initWithTitle:@"Creating a macOS App" isCompleted:NO],
        ]],
    ]];
    
    [self initView];
    [self bindHandler];
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(transformView:) name:UIKeyboardWillChangeFrameNotification object:nil];
    
}

// MARK: - Private

- (void)addBtnClick {
    GTDAddGroupViewController *addGroupVC = [[GTDAddGroupViewController alloc] init];
    addGroupVC.viewModel = self.viewModel;
    
    [self.navigationController pushViewController:addGroupVC animated:YES];
}

- (void)initView {
    self.view.backgroundColor = UIColor.whiteColor;
    
    self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemAdd target:self action:@selector(addBtnClick)];
    
    [self.view addSubview:self.tableView];
    
    [self.view addSubview:self.bottomView];
}

- (void)bindHandler {
    __weak typeof(self)weakSelf = self;
    
    self.viewModel.groupChangedHandler = ^{
        __strong typeof(weakSelf)strongSelf = weakSelf;
        [strongSelf.tableView reloadData];
    };
    
    self.viewModel.selectedGroupChangedHandler = ^{
        __strong typeof(weakSelf)strongSelf = weakSelf;
        [strongSelf.bottomView updateSelectedGroup:strongSelf.viewModel.selectedGroup];
    };
}

- (void)longPressGesture:(UILongPressGestureRecognizer *)rec {
    if (rec.state != UIGestureRecognizerStateBegan) {
        return;
    }
    
    CGPoint point = [rec locationInView:self.tableView];
    
    NSIndexPath *indexPath = [self.tableView indexPathForRowAtPoint:point];
    
    self.viewModel.editItem = [self.viewModel getGroups][indexPath.section].items[indexPath.row];
    
    GTDEditItemViewController *addItemVC = [[GTDEditItemViewController alloc] init];
    addItemVC.viewModel = self.viewModel;
    
    [self.navigationController pushViewController:addItemVC animated:YES];
}

- (void)transformView:(NSNotification *)notification {

    //获取键盘弹出前的Rect
    NSValue *keyBoardBeginBounds = [[notification userInfo] objectForKey:UIKeyboardFrameBeginUserInfoKey];
    CGRect beginRect = [keyBoardBeginBounds CGRectValue];

    //获取键盘弹出后的Rect
    NSValue *keyBoardEndBounds = [[notification userInfo] objectForKey:UIKeyboardFrameEndUserInfoKey];
    CGRect endRect = [keyBoardEndBounds CGRectValue];

    CGFloat deltaY = endRect.origin.y - beginRect.origin.y;

    if (self.bottomView.mode == GTDBottomViewModeEdit) {
        [UIView animateWithDuration:0.25f animations:^{
            CGRect frame = self.bottomView.frame;
            frame.origin.y += deltaY;

            self.bottomView.frame = frame;
        }];
    }
}

// MARK: - Getter && Setter

- (UITableView *)tableView {
    if (!_tableView) {
        UITableView *tableView = [[UITableView alloc] initWithFrame:CGRectMake(CGRectGetMinX(self.view.bounds), CGRectGetMinY(self.view.bounds), CGRectGetWidth(self.view.bounds), CGRectGetHeight(self.view.bounds) - 50) style:UITableViewStyleGrouped];
        tableView.delegate = self;
        tableView.dataSource = self;
        tableView.rowHeight = 44;
        [tableView registerClass:GTDItemCell.class forCellReuseIdentifier:@"GTDItemCell"];
        
        tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
        tableView.keyboardDismissMode = UIScrollViewKeyboardDismissModeOnDrag;
        
        UILongPressGestureRecognizer *longPress = [[UILongPressGestureRecognizer alloc] initWithTarget:self action:@selector(longPressGesture:)];
        [tableView addGestureRecognizer:longPress];
        
        {
            UISearchBar *searchBar = [[UISearchBar alloc] initWithFrame:CGRectMake(0, 0, CGRectGetWidth(self.view.frame), 34)];
            searchBar.barStyle = UIBarStyleDefault;
            searchBar.placeholder = @"input search text";
            searchBar.delegate = self;
            
            tableView.tableHeaderView = searchBar;
        }
        
        _tableView = tableView;
    }
    
    return _tableView;
}

- (GTDBottomView *)bottomView {
    if (!_bottomView) {
        GTDBottomView *bottomView = [[GTDBottomView alloc] initWithFrame:CGRectMake(0, CGRectGetHeight(self.view.bounds) - 50, CGRectGetWidth(self.view.bounds), 50)];
        bottomView.backgroundColor = self.tableView.backgroundColor;
        bottomView.addTodoTF.delegate = self;
        bottomView.viewModel = self.viewModel;
        
        _bottomView = bottomView;
    }
    
    return _bottomView;
}

// MARK: - UISearchBarDelegate

- (BOOL)searchBarShouldBeginEditing:(UISearchBar *)searchBar {
    if (self.bottomView.mode == GTDBottomViewModeEdit) {
        [self.bottomView.addTodoTF endEditing:YES];
        
        self.bottomView.mode = GTDBottomViewModeDefault;
        
        return NO;
    }
    return YES;
}

- (void)searchBar:(UISearchBar *)searchBar textDidChange:(NSString *)searchText {
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.3 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self.viewModel searchItemTitle:searchText];
    });
}

// MARK: - UITextFieldDelegate
- (void)textFieldDidEndEditing:(UITextField *)textField {
    self.bottomView.mode = GTDBottomViewModeDefault;
}

- (void)textFieldDidBeginEditing:(UITextField *)textField {
    self.bottomView.mode = GTDBottomViewModeEdit;
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    if (!textField.text.length) {
        return NO;
    }
    
    NSString *str = textField.text;
    [self.viewModel addGTDItem:str group:self.viewModel.selectedGroup];
    
    [textField resignFirstResponder];
    
    textField.text = nil;
    
    return YES;
}

// MARK: - UITableView Delegete && DataSource
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return [self.viewModel getGroups].count;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [self.viewModel getGroups][section].items.count;
}

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 35;
}
 
- (CGFloat)tableView:(UITableView *)tableView heightForFooterInSection:(NSInteger)section {
    return 0;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    UIView *view = [UIView new];
    
    UILabel *header = [[UILabel alloc] initWithFrame:CGRectMake(20, 0, CGRectGetWidth(self.view.bounds) - 20, 35)];
    header.text = [self.viewModel getGroups][section].groupName;
    header.font = [UIFont boldSystemFontOfSize:13];
    header.textColor = UIColor.blackColor;
    
    [view addSubview:header];
    
    return view;
}

- (UIView *)tableView:(UITableView *)tableView viewForFooterInSection:(NSInteger)section {
    return nil;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    return [self.viewModel getGroups][section].groupName;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    GTDItemCell *cell = [tableView dequeueReusableCellWithIdentifier:@"GTDItemCell"];
    
    cell.item = [self.viewModel getGroups][indexPath.section].items[indexPath.row];
    
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    GTDGroup *group = [self.viewModel getGroups][indexPath.section];
    GTDItem *item = group.items[indexPath.row];
    
    [self.viewModel updateItemCompleted:item group:group];
}

@end
