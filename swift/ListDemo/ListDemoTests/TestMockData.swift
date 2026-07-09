//
//  TestMockData.swift
//  ListDemoTests
//
//  Created by 王明友 on 2023/6/14.
//

import Foundation

class TestMockData {
    static func mockData() -> [AppListViewModel] {
        var apps = [AppListViewModel]()
        let imgs = ["https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/a3/c4/62/a3c4624b-b16a-ceac-2cde-ed96694f1247/AppIcon-0-1x_U007emarketing-0-4-0-sRGB-0-85-220.png/60x60bb.jpg", "https://is2-ssl.mzstatic.com/image/thumb/Purple126/v4/70/40/52/7040523d-97c1-ba3e-9d14-477f967149f1/AppIcon-0-1x_U007emarketing-0-7-0-85-220.png/60x60bb.jpg", "https://is3-ssl.mzstatic.com/image/thumb/Purple126/v4/cc/fb/d3/ccfbd323-ec3a-8602-f4c4-7c9766c8b1d6/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/60x60bb.jpg"]
        
        for i in 0 ..< 20 {
            let viewModel = AppListViewModel(app: AppItem(
                id: (1000 + i) ,
                iconUrl:imgs[Int(arc4random()) % imgs.count],
                name: "Test Name \(i)",
                description: """
                your selected plan. You can manage your subscription and turn off auto-renewal at any time by going to your iTunes account settings on your device.\n\nViber is part of the Rakuten Group, a world leader in e-commerce and financial services.
                """
            ))
            apps.append(viewModel)
        }
        return apps
    }
}
