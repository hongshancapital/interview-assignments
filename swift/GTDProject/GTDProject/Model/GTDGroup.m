//
//  GTDGroup.m
//  GTDProject
//
//  Created by fang on 2021/12/6.
//

#import "GTDGroup.h"

@implementation GTDGroup

- (instancetype)initWithName:(NSString *)name items:(NSArray<GTDItem *> *)items {
    self = [super init];
    
    if (self) {
        _groupName = name.copy;
        _items = items.copy;
    }
    
    return self;
}

- (void)removeItem:(GTDItem *)item; {
    NSMutableArray *mutableItems = self.items.mutableCopy;
    
    [mutableItems removeObject:item];
    
    self.items = mutableItems.copy;
}

- (void)sortItems {
    self.items = [self.items sortedArrayUsingComparator:^NSComparisonResult(GTDItem *obj1, GTDItem *obj2) {
        return obj1.completed > obj2.completed;
    }];
}

- (id)copyWithZone:(nullable NSZone *)zone {
    return [[GTDGroup alloc] initWithName:self.groupName items:self.items];;
}
@end
