//
//  MockAppListData.swift
//  AppGalleryTests
//
//  Created by X Tommy on 2022/8/11.
//

import Foundation
@testable import AppGallery

enum AppListSuccessfulMock {
    
    private static let testApp0 = ChatApp(id: 0,
                                        name: "test0",
                                        iconUrl: "https://is5-ssl.mzstatic.com/image/thumb/Purple122/v4/2c/ff/0f/2cff0f08-85a7-898d-d209-6f47e43d8ac5/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/60x60bb.jpg",
                                        description: "test0")
    
    private static let testApp1 = ChatApp(id: 1,
                                        name: "test1",
                                        iconUrl: "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/d0/73/74/d073742d-f3c4-132b-406a-879915c5650e/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/60x60bb.jpg",
                                        description: "test1")
    
    private static let testApp2 = ChatApp(id: 2,
                                        name: "test2",
                                        iconUrl: "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/d0/73/74/d073742d-f3c4-132b-406a-879915c5650e/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/60x60bb.jpg",
                                        description: "test2")
    
    private static let testApp3 = ChatApp(id: 3,
                                        name: "test3",
                                        iconUrl: "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/d0/73/74/d073742d-f3c4-132b-406a-879915c5650e/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/60x60bb.jpg",
                                        description: "test3")
    
    private static let testApp4 = ChatApp(id: 4,
                                        name: "test4",
                                        iconUrl: "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/d0/73/74/d073742d-f3c4-132b-406a-879915c5650e/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/60x60bb.jpg",
                                        description: "test4")
    
    static let list = [testApp0, testApp1, testApp2, testApp3, testApp4]
    
    static let listOfOne = Array(list.prefix(1))
    static let listOfTwo = Array(list.prefix(2))
    static let listOfThree = Array(list.prefix(3))
    static let listOfFour = Array(list.prefix(4))
    
}
