//
//  GTDViewModel.h
//  GTDProject
//
//  Created by fang on 2021/12/6.
//

#import <Foundation/Foundation.h>
#import "GTDGroup.h"

NS_ASSUME_NONNULL_BEGIN

typedef void(^GTDGroupChangedHandler)(void);
typedef void(^GTDSelectedGroupChangedHandler)(void);

@interface GTDViewModel : NSObject

@property (nonatomic, copy) GTDGroupChangedHandler groupChangedHandler;
@property (nonatomic, copy) GTDSelectedGroupChangedHandler selectedGroupChangedHandler;

@property (nonatomic, strong) GTDGroup *selectedGroup;

@property (nonatomic, strong) GTDItem *editItem;

- (instancetype)initWithGroups:(NSArray<GTDGroup *> *)groups;

- (NSArray<GTDGroup *> *)getGroups;

- (void)addGTDItem:(NSString *)title group:(GTDGroup *)group;

- (void)addGTDGroup:(NSString *)groupName;

- (void)updateItemCompleted:(GTDItem *)item group:(GTDGroup *)group;

- (void)updateItemTitle:(NSString *)title item:(GTDItem *)item;

- (void)searchItemTitle:(NSString *)title;

@end

NS_ASSUME_NONNULL_END
