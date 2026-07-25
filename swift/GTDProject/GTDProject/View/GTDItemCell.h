//
//  GTDItemCell.h
//  GTDProject
//
//  Created by fang on 2021/12/6.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@class GTDItem;

@interface GTDItemCell : UITableViewCell

@property (nonatomic, strong) GTDItem *item;

@end

NS_ASSUME_NONNULL_END
