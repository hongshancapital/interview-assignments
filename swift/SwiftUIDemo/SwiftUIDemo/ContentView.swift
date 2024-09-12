//
//  ContentView.swift
//  SwiftUIDemo
//
//  Created by HBC on 2022/2/18.
//

import SwiftUI
import SDWebImageSwiftUI
import BBSwiftUIKit

struct ContentView: View {
    @State private var appList: [AppInfo] = []
    
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false
    
    @State private var listState = ListState()
    
    var body: some View {
        NavigationView {
            AppListView()
            .navigationBarTitle(Text("APP"))
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
