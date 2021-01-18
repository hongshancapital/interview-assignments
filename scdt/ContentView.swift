//
//  ContentView.swift
//  scdt
//
//  Created by qiupeng on 2021/1/18.
//

import SwiftUI

struct SearchBar: View {

    @Binding
    var text: String

    @Binding
    var isBeginSearch: Bool

    var body: some View {
        return HStack {
            Image(systemName: "magnifyingglass").padding(.leading, 10)
            TextField.init("Tap here to search", text: $text).keyboardType(.webSearch)
        }
        .frame(width: nil, height: 40, alignment: .center)
        .background(Color.gray.opacity(0.1))
        .cornerRadius(12)
        .padding(.horizontal, 12)
    }
}

struct SearchListSectionView: View {

    @State
    var data: SearchListSectionModel

    var body: some View {
        return VStack.init(alignment: .leading, spacing: 0) {
            HStack {
                Text(data.sectionName).padding(.horizontal, 14).frame(width: nil, height: 35, alignment: .leading)
                Spacer()
            }
            .background(Color.gray.opacity(0.1))
            ForEach.init(data.list) {item in
                HStack() {
                    VStack(alignment: .leading) {
                        Text(item.name).font(.system(size: 20)).bold()
                        Text(item.inStock ? "In-stock" : "Out-of-stock").foregroundColor(UIColor.systemGray.ui).font(.system(size: 12))
                    }
                    Spacer()
                    HStack {
                        Text(item.price)
                            .font(.system(size: 10))
                            .foregroundColor(item.inStock ? UIColor.systemBlue.ui : UIColor.systemGray.ui)
                            .padding(.all, 5)
                    }
                    .background(item.inStock ? UIColor.systemBlue.ui.opacity(0.2) : UIColor.systemGray.ui.opacity(0.2))
                    .cornerRadius(20)

                }
                .frame(width: nil, height: 55)
                .padding(.horizontal, 16)
            }
        }
    }
}

struct ContentView: View {

    @ObservedObject
    var vm: SearchViewModel

    @State
    var isBeginSearch: Bool = false

    @State
    var searchText: String = ""

    var body: some View {
        NavigationView {
            VStack {
                SearchBar(text: $searchText, isBeginSearch: $isBeginSearch)
                    .onChange(of: searchText, perform: { value in
                        //vm.search(value)
                })
                if vm.searchList.count <= 0 && searchText.count > 0 {
                    VStack {
                        Text("No result").foregroundColor(UIColor.systemGray.ui.opacity(0.5)).padding(.top, 70)
                    }

                }
                ForEach(vm.searchList) {item in
                    SearchListSectionView.init(data: item)
                }
                Spacer()
            }
            .navigationBarTitle(Text("Search"))
            .navigationBarHidden(searchText.count > 0)
        }
        .onAppear(perform: {
            NotificationCenter.default.addObserver(forName: UIResponder.keyboardWillHideNotification, object: nil, queue: nil) { (no) in
                vm.search(searchText)
            }
        })
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView.init(vm: SearchViewModel.init())
    }
}
