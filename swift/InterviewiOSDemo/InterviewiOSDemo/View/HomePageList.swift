//
//  HomePageList.swift
//  InterviewiOSDemo
//
//  Created by 寇云鹏 on 2022/5/6.
//

import SwiftUI

struct HomePageList: View {
    
    // 绑定状态机中首页数据状态
    @EnvironmentObject var store: Store
    var homePageDataBinding :Binding<AppState.HomePageData> {
        $store.appState.homePageData
    }
    var homePageData: AppState.HomePageData {
        store.appState.homePageData
    }
    
    var body: some View {
        ScrollView {
            ForEach(homePageData.allHomePageList) { model
                in
                HomePageRow(model: model)
                    .background(Color.clear)
                    .padding(
                        EdgeInsets(
                            top: 0,
                            leading: 15,
                            bottom: 5,
                            trailing: 15
                        )
                    )
            }
        }
        .frame(
            minWidth: 0,
            maxWidth: .infinity,
            minHeight: 0,
            maxHeight: .infinity
        )
        .background(
            Color
                .init(
                    red: 244.0 / 255.0,
                    green: 244.0 / 255.0,
                    blue: 247.0 / 255.0
                )
        )
    }
}

struct HomePageList_Previews: PreviewProvider {
    static var previews: some View {
        let store = Store()
//        store.appState.homePageData
        return HomePageList().environmentObject(store)
    }
}
