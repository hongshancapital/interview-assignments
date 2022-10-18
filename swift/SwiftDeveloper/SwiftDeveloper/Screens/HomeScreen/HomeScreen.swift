//
//  HomeScreen.swift
//  SwiftDeveloper
//
//  Created by zhe wu on 2022/10/18.
//

import SwiftUI

struct HomeScreen: View {
    @StateObject var homeViewModel = HomeViewModel()
    
    var body: some View {
        List(homeViewModel.appsModel.results, id: \.trackId) {
            Text($0.trackName)
        }
        .navigationTitle("App")
        .refreshable {
            print("hello")
        }
    }
}