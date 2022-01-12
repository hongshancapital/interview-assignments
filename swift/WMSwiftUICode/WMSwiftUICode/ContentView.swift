//
//  ContentView.swift
//  WMSwiftUICode
//
//  Created by wangmm on 2022/1/5.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        WMTodoListView()
            .environmentObject(WMModelData())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
