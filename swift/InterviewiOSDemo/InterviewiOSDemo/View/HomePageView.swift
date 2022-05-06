//
//  HomePageView.swift
//  InterviewiOSDemo
//
//  Created by 寇云鹏 on 2022/5/6.
//

import SwiftUI

/// 首页列表根视图
struct HomePageView: View {
    
    @EnvironmentObject var store: Store
    
    var body: some View {
        NavigationView {
            HomePageList()
                .navigationBarTitle("App")
        }
        .onAppear {
            self.store.dispatch(.loadHomePage)
        }
    }
}

struct PokemonListRoot_Previews: PreviewProvider {
    static var previews: some View {
        HomePageView()
    }
}
