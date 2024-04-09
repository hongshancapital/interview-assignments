//
//  ContentView.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/10.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        AppListRootView().environmentObject(AppStore())
    }
    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}





