//
//  GTDViewModel.m
//  GTDProject
//
//  Created by fang on 2021/12/6.
//

#import "GTDViewModel.h"


@interface GTDViewModel ()

@property (nonatomic, strong, readwrite) NSMutableArray<GTDGroup *> *groups;

@property (nonatomic, strong) NSMutableArray<GTDGroup *> *searchGroups;

@property (nonatomic, copy) NSString *searchText;

@end

@implementation GTDViewModel

@synthesize selectedGroup = _selectedGroup;

- (instancetype)initWithGroups:(NSArray<GTDGroup *> *)groups {
    self = [super init];
    
    if (self) {
        _groups = groups.mutableCopy;
    }
    
    return self;
}

- (void)addGTDItem:(NSString *)title group:(GTDGroup *)group {
    NSUInteger indexOfGroup = [self.groups indexOfObject:group];
    
    if (indexOfGroup == NSNotFound) {
        return;
    }
    
    NSMutableArray *mutableItems = self.groups[indexOfGroup].items.mutableCopy;
    
    [mutableItems insertObject:[[GTDItem alloc] initWithTitle:title isCompleted:NO] atIndex:0];
    
    self.groups[indexOfGroup].items = mutableItems.copy;
    
    if (self.groupChangedHandler) {
        self.groupChangedHandler();
    }
}

- (void)addGTDGroup:(NSString *)groupName {
    [self.groups addObject:[[GTDGroup alloc] initWithName:groupName items:@[]]];
    
    if (self.groupChangedHandler) {
        self.groupChangedHandler();
    }
}

- (void)updateItemCompleted:(GTDItem *)item group:(GTDGroup *)group {
    NSUInteger indexOfGroup = [self.groups indexOfObject:group];
    
    if (indexOfGroup == NSNotFound) {
        return;
    }
    
    NSUInteger indexOfItem = [self.groups[indexOfGroup].items indexOfObject:item];
    
    if (indexOfItem == NSNotFound) {
        return;
    }
    
    item.completed = !item.completed;
    
    [group sortItems];
    
    if (self.groupChangedHandler) {
        self.groupChangedHandler();
    }
}

- (void)updateItemTitle:(NSString *)title item:(GTDItem *)item {
    for (GTDGroup *group in self.groups) {
        if (![group.items containsObject:item]) {
            continue;
        }
        
        NSUInteger indexOfItem = [group.items indexOfObject:item];
        
        if (indexOfItem == NSNotFound) {
            return;
        }
        
        if (title.length) {
            item.title = title;
        } else {
            [group removeItem:item];
        }
        
        if (self.groupChangedHandler) {
            self.groupChangedHandler();
        }
    }
}

- (void)searchItemTitle:(NSString *)title {
    self.searchText = title;
    
    if (title.length) {
        NSMutableArray *mutableArray = [[NSMutableArray alloc] initWithArray:self.groups copyItems:YES];
        
        for (GTDGroup *group in mutableArray) {
            for (GTDItem *item in group.items) {
                if (![item.title localizedCaseInsensitiveContainsString:title]) {
                    [group removeItem:item];
                }
            }
        }
        
        self.searchGroups = mutableArray;
    }
    
    if (self.groupChangedHandler) {
        self.groupChangedHandler();
    }
}

// MARK: - Getter && Setter

- (NSArray<GTDGroup *> *)getGroups {
    return self.searchText.length ? self.searchGroups.copy : self.groups.copy;
}

- (GTDGroup *)selectedGroup {
    return _selectedGroup ? _selectedGroup : self.groups.firstObject;
}

- (void)setSelectedGroup:(GTDGroup *)selectedGroup {
    _selectedGroup = selectedGroup;
    
    if (self.selectedGroupChangedHandler) {
        self.selectedGroupChangedHandler();
    }
}
@end
