import SwiftUI

struct SearchListView: View {
    
    /// 搜索文本
    @State var searchText = ""
    
    @StateObject var searchViewModel = SearchViewModel()
        
    var body: some View {
        NavigationView {
            List {
                ForEach(searchViewModel.searchList) { data in
                    Section(data.title) {
                        ForEach(data.items) { item in
                            SearchItemView(item: item)
                                .listRowSeparator(data.items.last == item ? .hidden : .visible)
                                .listRowSeparatorTint(Color.gray.opacity(0.2))
                                .padding(.vertical, 5)
                        }
                    }
                    .listSectionSeparator(.hidden)
                    .padding(.vertical, 5)
                }
            }
            .listStyle(PlainListStyle())
            .background(Color.gray.opacity(0.08))
            .overlay(alignment: .top) {
                NoResultView()
                    .opacity(searchViewModel.searchList.isEmpty && !searchText.isEmpty ? 1.0 : 0.0)
                    .padding(.top, 80)
            }
            .onChange(of: searchText) { newValue in
                searchViewModel.searchKeyword = newValue
            }
            .navigationTitle("Search")
        }.searchable(text: $searchText, prompt: "Tap here to search")
            
    }
    
}
