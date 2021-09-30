//
//  Home.swift
//  LocalServerApp
//
//  Created by 梁泽 on 2021/9/30.
//

import SwiftUI

struct HomeView: View {
    @StateObject var vm = SearchVM()
    
    @State private var query = ""
    @Environment(\.isSearching) private var isSearching: Bool
    
    var isShowPlaceHolder: Bool {
        !query.isEmpty && vm.list.isEmpty
    }
    
    var body: some View {
        NavigationView {
            List {
                ForEach(vm.list) { model in
                    Section(model.title) {
                        ForEach(model.items) { item in
                            SearchItemView(model: item)
                                .padding(.vertical, 12)
                                .listRowSeparator(model.items.last == item ? .hidden : .visible)
                        }
                    }
                }
            }
            .background(Color.gray.opacity(0.1))
            .overlay(alignment: .top, content: {
                Text("No result")
                    .foregroundColor(.gray)
                    .opacity(isShowPlaceHolder ? 1 : 0)
                    .padding(.top, 60)
            })
            .listStyle(PlainListStyle())
            .onChange(of: query, perform: { newValue in
                vm.searchKey = newValue
            })
            .navigationTitle("Search")
            
            .overlay {
                if isSearching && !query.isEmpty {
                    Rectangle()
                }
            }
            
        } // NavigationView end
        .searchable(text: $query, prompt: "Tap here to search")

        
            
    }
}

struct Home_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
