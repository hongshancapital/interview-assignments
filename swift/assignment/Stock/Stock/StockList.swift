import SwiftUI
import UIPlus

struct StockList: View {

    init(data: Binding<[StockRecordGroup]>, onKeywordChanged: @escaping (String) -> Void = { _ in }) {
        _data = data
        _onKeywordChanged = onKeywordChanged
    }

    @Binding
    var data: [StockRecordGroup]

    let _onKeywordChanged: (String) -> Void

    @State
    var keyword = ""

    @State
    var isSearching = false

    var body: some View {
        NavigationView {
            VStack {
                SearchBar(title: "Tap here to search", text: Binding {
                    keyword
                } set: {
                    keyword = $0
                    _onKeywordChanged($0)
                }, isEditing: $isSearching)
                content()
                Spacer()
            }
            .navigationBarTitle("Search")
            .navigationBarHidden(isSearching || !keyword.isEmpty)
        }
        .animation(.default, value: isSearching || !keyword.isEmpty)
    }

    @ViewBuilder
    func content() -> some View {
        if keyword.isEmpty {
            EmptyView()
        } else if data.isEmpty {
            Text("No result")
                .font(.body)
                .foregroundColor(.gray)
                .padding(.top, 40)
        } else {
            List {
                ForEach(data, id: \.name) {
                    section(recordGroup: $0)
                }
            }
            .listStyle(PlainListStyle())
        }
    }

    func section(recordGroup: StockRecordGroup) -> some View {
        Section(header: headerText(recordGroup.name)) {
            ForEach(recordGroup.records, id: \.name) {
                StockRow(record: $0)
            }
        }
    }

    func headerText(_ string: String) -> some View {
        Text(string)
            .font(.headline)
            .foregroundColor(.gray)
    }
}
