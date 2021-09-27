//
//  SearchView.swift
//  scdt-homework
//
//  Created by Chr1s on 2021/9/26.
//

import SwiftUI

// MARK: - 搜索视图
struct SearchView: View {
    
    @EnvironmentObject var vm: ViewModel
    @State var text: String = ""
  
    var body: some View {
        
        ZStack {
            
            Color(.systemGray6).edgesIgnoringSafeArea(.all)
            
            VStack {
                searchBarView()
                
                if let list = vm.listData {
                    if list != [] {
                        List {
                            searchResultView(list)
                        }
                        .listStyle(GroupedListStyle())
                        .background(Color.clear)
                    } else {
                        Spacer()
                        Text("No result")
                            .bold()
                    }
                }
                
                Spacer()
            }
        }
        .onAppear {
            UITableView.appearance().backgroundColor = .systemGray6
        }
    }
}

extension SearchView {
    // MARK: - 搜索栏
    func searchBarView() -> some View {
        HStack {
            FirstResponderTextField(text: $text, placeholder: "Tap here to search", onCommit: {
                /// 启动搜索Combine链
                if !text.isEmpty {
                    vm.searchPublisher.send(text)
                }
            })
            .frame(height: 20)
            .autocapitalization(.none)
            
            Spacer()
            Image(systemName: "xmark")
                .onTapGesture {
                    vm.isSearch = false
                }
        }
        .padding()
        .background(Color.gray.opacity(0.2))
        .clipShape(RoundedRectangle(cornerRadius: 14))
        .padding()
    }
}

extension SearchView {
    // MARK: - 搜索结果列表
    func searchResultView(_ list: StockInfo) -> some View {
        ForEach(list, id: \.self) { item in
            Section(header: Text(item.category)) {
                ForEach(item.content, id: \.self) { content in
                    searchResultContent(content)
                }
            }
        }
    }
    
    // MARK: - 搜索结果列表内容行
    func searchResultContent(_ content: Content) -> some View {
        HStack {
            VStack(alignment: .leading) {
                Text(content.name)
                    .bold()
                
                Text(content.type)
                    .font(.subheadline)
                    .foregroundColor(.gray)
            }
            
            Spacer()
            Text("$\(content.price, specifier: "%.2f")")
                .foregroundColor(content.frozen == 1 ? .blue : .gray)
                .padding(.horizontal, 15)
                .padding(.vertical, 5)
                .background(content.frozen == 1 ? Color.blue.opacity(0.2) : Color.gray.opacity(0.2))
                .clipShape(RoundedRectangle(cornerRadius: 12))
                .padding(12)
        }
    }
}


