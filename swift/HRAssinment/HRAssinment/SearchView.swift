//
//  SearchView.swift
//  HRAssinment
//
//  Created by Henry Zhang on 2021/10/21.
//

import SwiftUI

struct SearchView : View {
    @StateObject var vm = SearchViewModel()
    
    @State private var query = ""
    @Environment(\.isSearching) private var isSearching: Bool
    
    var shouldShowPlaceHolder: Bool {
        !query.isEmpty && vm.list.isEmpty
    }
    
    var body: some View {
        NavigationView {
                    List {
                        ForEach(vm.list) { model in
                            Section(model.title) {
                                ForEach(model.items) { item in
                                    SearchResultView(model: item)
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
                            .opacity(shouldShowPlaceHolder ? 1 : 0)
                            .padding(.top, 60)
                    })
                    .listStyle(PlainListStyle())
                    .onChange(of: query, perform: { newValue in
                        vm.searchString = newValue
                    })
                    .navigationTitle("Search")
                    
                    .overlay {
                        if isSearching && !query.isEmpty {
                            Rectangle()
                        }
                    }
                    
                }
                .searchable(text: $query, prompt: "Tap here to search")
    }
}

//struct SearchView_Previews: PreviewProvider {
//    static var previews: some View {
//        SearchView()
//    }
//}
