//
//  ContentView.swift
//  memo
//
//  Created by LI on 2021/11/14.
//

import SwiftUI

fileprivate class DataSource: ObservableObject {
    @Published var sections = [MemoSection]()
    
    @Published var searching: Bool = false
    
    @Published var keyword: String = ""
    
    init() {
        sections = MemoDataSource.default.sections
    }
    
    func search() {
        guard keyword.isEmpty == false else {
            sections = MemoDataSource.default.sections
            searching = false
            return
        }
        var _sections = [MemoSection]()
        if keyword.isEmpty { return }
        let data = MemoDataSource.default.sections
        data.forEach { section in
            var _memos = [MemoItem]()
            section.items.forEach { item in
                if item.content.contains(self.keyword) {
                    _memos.append(item)
                }
            }
            if _memos.isEmpty == false {
                let sec = MemoSection(title: section.title, items: _memos)
                _sections.append(sec)
            }
        }
        sections = _sections
    }
}

struct MemoListView: View {
    
    @StateObject fileprivate var data = DataSource()
    
    var body: some View {
        NavigationView {
            ZStack {
                VStack {
                    tableView()
                    Spacer(minLength: 40)
                }
                if data.searching {
                    MemoSearchBar(edit: $data.searching, text: $data.keyword, placeholder: "Search Memo...", change: nil) { value in
                        data.search()
                    }
                } else {
                    AddNewMemoView()
                }
            }
            .toolbar {
                rightBar()
            }
        }
    }
    
    private func tableView() -> some View {
        List(data.sections) { section in
            Section {
                ForEach(section.items) { item in
                    MemoView(item: item)
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.clear)
            } header: {
                Text(section.title)
                    .font(.system(size: 18))
                    .bold()
                    .foregroundColor(Color.black)
            }
        }
        .listStyle(.grouped)
//        .navigationBarTitle("List")
    }
    
    private func rightBar() -> some View {
        Button {
            data.searching = true
        } label: {
            Image(systemName: "magnifyingglass")
        }
    }
}

struct MemoListView_Previews: PreviewProvider {
    
    static var previews: some View {
        MemoListView()
    }
}
