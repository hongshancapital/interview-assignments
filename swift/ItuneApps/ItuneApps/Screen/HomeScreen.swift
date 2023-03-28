//
//  ContentView.swift
//  HomeScreen
//
//  Created by daicanglan on 2023/3/27.
//

import Combine
import SwiftUI
struct HomeScreen: View {
    var body: some View {
        NavigationView {
            AppListView()
                .navigationTitle("App")
        }
    }
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
    }
}
