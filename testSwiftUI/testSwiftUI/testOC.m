//
//  testOC.m
//  testSwiftUI
//
//  Created by pchen on 2023/3/17.
//

#import <Foundation/Foundation.h>

typedef  struct TreeNote
{
    struct TreeNote *leftNote;
    struct TreeNote *rightNote;
    id data;
}TreeNote;


@interface ABCreate: NSObject

@end


@implementation ABCreate: NSObject

- (void)hello
{
    TreeNote *next;
}




@end
