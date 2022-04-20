//
//  ContentView.swift
//  SwiftUIDemo
//
//  Created by didi_qihang on 2022/3/13.
//

import SwiftUI

struct ContentView: View {
    
    @StateObject var vm = ContentViewModel()
    
    var body: some View {
        NavigationView {
            List {
                ForEach(vm.items) { item in
                    ItemView(item: item)
                        .listRowBackground(Color.clear)
                        .listRowSeparator(.hidden)
                        .listRowInsets(EdgeInsets(top: 8, leading: 0, bottom: 8, trailing: 0))
                }
            }
            .navigationTitle(Text("App"))
            .onAppear {
                self.vm.getItemList()
            }
            .refreshable {
                self.vm.getItemList()
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

