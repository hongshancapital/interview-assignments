//
//  ContentView.swift
//  AppList
//
//  Created by mengyun on 2022/3/20.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        ListView(vm: ListViewModel())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
