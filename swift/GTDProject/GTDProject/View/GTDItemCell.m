//
//  GTDItemCell.m
//  GTDProject
//
//  Created by fang on 2021/12/6.
//

#import "GTDItemCell.h"
#import "GTDItem.h"

@interface GTDItemCell ()

@property (nonatomic, strong) UIImageView *iv;
@property (nonatomic, strong) UILabel *titleLabel;

@end

@implementation GTDItemCell

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    
    if (self) {
        self.backgroundColor = [UIColor colorWithRed:40 green:40 blue:40 alpha:0];
        self.selectionStyle = UITableViewCellSelectionStyleNone;
        
        UIView *contentView = [[UIView alloc] initWithFrame:CGRectMake(20, 5, CGRectGetWidth(self.bounds) - 40, CGRectGetHeight(self.bounds) - 10)];
        contentView.backgroundColor = UIColor.whiteColor;
        contentView.layer.cornerRadius = 8;
        contentView.layer.masksToBounds = YES;
        [self addSubview:contentView];
        
        UIImageView *iv = [[UIImageView alloc] initWithFrame:CGRectMake(10, 7, 20, 20)];
        [contentView addSubview:iv];
        self.iv = iv;
        
        UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake(40, 0, CGRectGetWidth(self.bounds) - 50, CGRectGetHeight(contentView.bounds))];
        label.font = [UIFont boldSystemFontOfSize:13];
        label.textColor = UIColor.blackColor;
        [contentView addSubview:label];
        self.titleLabel = label;
    }
    
    return self;
}

- (void)setItem:(GTDItem *)item {
    _item = item;
    
    self.iv.image = [UIImage imageNamed:item.completed ? @"选中" : @"未选中"];
    
    NSMutableAttributedString *attrString = [[NSMutableAttributedString alloc] initWithString:item.title];
    if (item.completed) {
        [attrString addAttribute:NSStrikethroughStyleAttributeName value:@(NSUnderlinePatternSolid | NSUnderlineStyleSingle) range:NSMakeRange(0, item.title.length)];
        [attrString addAttribute:NSForegroundColorAttributeName value:UIColor.grayColor range:NSMakeRange(0, item.title.length)];
    } else {
        [attrString addAttribute:NSForegroundColorAttributeName value:UIColor.blackColor range:NSMakeRange(0, item.title.length)];
    }
    
    self.titleLabel.attributedText = attrString;
    
}

@end
