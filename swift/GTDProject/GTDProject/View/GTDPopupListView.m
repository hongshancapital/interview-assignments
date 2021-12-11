//
//  GTDPopupListView.m
//  GTDProject
//
//  Created by fang on 2021/12/11.
//

#import "GTDPopupListView.h"

@interface GTDPopupListView ()<UITableViewDelegate,UITableViewDataSource>

@property (nonatomic ,strong) UITableView *tableView;

@property (nonatomic, strong) GTDViewModel *viewModel;

@end

@implementation GTDPopupListView

- (instancetype)initWithViewModel:(GTDViewModel *)viewModel {
    self = [super initWithFrame:CGRectMake(0, 0, 150, viewModel.getGroups.count*44)];
    if (self) {
        
        self.viewModel = viewModel;
        [self addSubview:self.tableView];
    }
    return self;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
    static NSString *cellIdentifier = @"cellIdentifier";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:cellIdentifier];
        cell.backgroundColor = [UIColor clearColor];
        cell.textLabel.textColor = [UIColor whiteColor];
        UIView *lineView = [[UIView alloc] initWithFrame:CGRectMake(0, 43.5, 150, .5)];
        lineView.backgroundColor = [UIColor lightGrayColor];
        [cell.contentView addSubview:lineView];
    }
    
    cell.textLabel.text = [self.viewModel.getGroups objectAtIndex:indexPath.row].groupName;
    cell.textLabel.font = [UIFont systemFontOfSize:10];

    return cell;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section{
    return self.viewModel.getGroups.count;
}
- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    [tableView deselectRowAtIndexPath:indexPath animated:YES];
    
    self.viewModel.selectedGroup = self.viewModel.getGroups[indexPath.row];
    
    if (self.dismissHandler) {
        self.dismissHandler();
    }
}

- (UITableView *)tableView{
    if (_tableView == nil) {
        _tableView = [[UITableView alloc] initWithFrame:self.bounds style:UITableViewStylePlain];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        _tableView.backgroundColor = [UIColor clearColor];
        _tableView.scrollEnabled = NO;
        _tableView.showsVerticalScrollIndicator = NO;
        _tableView.separatorStyle = UITableViewCellSeparatorStyleNone;
    }
    return _tableView;
}
@end
