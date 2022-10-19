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
        ZStack {
            if homeViewModel.firstLoading {
                ProgressView()
            } else {
//                List(homeViewModel.appsModel.results, id: \.trackId) {
//                    Text($0.trackName)
//                        .listRowBackground(Color.clear)
//                }
//                .refreshable {
//                    homeViewModel.fetchData(isRerefeshing: true)
//                }

                List {
                    ForEach(homeViewModel.appsModel.results, id: \.trackId) {
                        AppRow(appModel: $0)
//                        Text($0.trackName)
                    }
                    .listRowInsets(.init())
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.clear)
//                    .padding(.vertical, 1)
//                    .background(Color(UIColor.red))
                }
                .listStyle(.plain)
                .environment(\.defaultMinListRowHeight, 1)
                .padding(.horizontal)
                .refreshable {
                    homeViewModel.fetchData(isRerefeshing: true)
                }
            }
        }
        .background(Color(UIColor.systemGroupedBackground))
        .navigationTitle("App")
        .onAppear {
            homeViewModel.fetchFirstTime()
        }
        .animation(.easeInOut, value: homeViewModel.firstLoading)
    }
}
