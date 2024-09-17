//
//  GTDGroup.h
//  GTDProject
//
//  Created by fang on 2021/12/6.
//

#import <Foundation/Foundation.h>
#import "GTDItem.h"

NS_ASSUME_NONNULL_BEGIN

@interface GTDGroup : NSObject<NSCopying>

@property (nonatomic, copy) NSArray<GTDItem *> *items;
@property (nonatomic, copy) NSString *groupName;

- (instancetype)initWithName:(NSString *)name items:(NSArray<GTDItem *> *)items;

- (void)removeItem:(GTDItem *)item;

- (void)sortItems;

@end

NS_ASSUME_NONNULL_END
