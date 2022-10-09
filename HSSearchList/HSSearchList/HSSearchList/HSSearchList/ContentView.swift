//
//  ContentView.swift
//  HSSearchList
//
//  Created by dongxia zhu on 2021/10/8.
//

import SwiftUI
import UIKit

struct ContentView: View {
    @State private var searchText: String = ""
    @State private var searchResult: [HSResultModel] = []
    var body: some View {
        NavigationView {
            if #available(iOS 15.0, *) {
                ZStack {
                        if requestSearchResult(searchText){
                            if searchText.count > 0 && searchResult.count == 0 {
                                VStack {
                                    Text("No result")
                                        .font(.title2)
                                        .fontWeight(.regular)
                                        .foregroundColor(titleColor)
                                }
                                .frame(maxWidth: .infinity, maxHeight: 100, alignment: .top)
                            } else {
                                List {
                                    ForEach(searchResult) { department in
                                        HSListHeader(title: department.category.rawValue)
                                            .frame(height: 30)
                                            .listRowBackground(backgroundColor)
                                        ForEach(department.itemData) { item in
                                            HSResultRow(item: item).frame(height: 60)
                                        }
                                    }
                                }
                                .listRowInsets(EdgeInsets())
                                .listStyle(PlainListStyle())
                            }
                    }
                }
                 .navigationTitle(Text("Search"))
                 .searchable(text: $searchText, prompt: "Tap here to search")
                if configSearchView() {}
                 
            } else {
                VStack {
                    HSSearchBar(text: $searchText)
                        .padding(.horizontal, 15)
                    if searchText.count > 0 && requestSearchResult(searchText){
                        HSResultList(results: $searchResult)
                    }
                }
                .frame(
                    maxWidth: .infinity,
                    maxHeight: .infinity,
                    alignment: .topLeading
                )
                .background(backgroundColor.ignoresSafeArea())
                .navigationBarTitle(Text("Search"))
                .navigationBarHidden(searchText.count > 0)
            }
        }
        
    }
    
    func configSearchView() -> Bool {
        UISearchBar.appearance().setImage(UIImage(named: "mySearchClearBtn"), for: .clear, state: .normal)
        UISearchBar.appearance().setImage(UIImage(named: "mySearchClearBtn"), for: .clear, state: .highlighted)
        UISearchBar.appearance().showsCancelButton = false
        return true
    }
    
    func requestSearchResult(_ text: String) -> Bool {
        ResultData().requestSearchResult(text) {resData in
            self.searchResult = resData
        }
        return true
    }
}

