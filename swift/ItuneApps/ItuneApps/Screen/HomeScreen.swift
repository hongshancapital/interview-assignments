//
//  ContentView.swift
//  HomeScreen
//
//  Created by daicanglan on 2023/3/27.
//

import SwiftUI
import Combine
struct HomeScreen: View {
    @EnvironmentObject var vm: AppViewModel
    
    var models = AppItem.examples

    var body: some View {
        
        NavigationView {
            AppListView()
                .navigationTitle("App")
                .environmentObject(AppViewModel())
        }
        
    }
   
}

struct HomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen()
            .environmentObject(AppViewModel())
    }
}
