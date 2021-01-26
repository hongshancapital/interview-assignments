import SwiftUI
import Combine

struct ProductGroup: Hashable, Decodable {
    var category: String
    var products: [Product]
}

struct ResultSection: View {
    var productGroup: ProductGroup
    var body: some View {
        Section(header: Text(self.productGroup.category)
                    .font(.subheadline)) {
            ForEach(self.productGroup.products, id: \.self) { product in
                ProductView(product: product)
            }
        }.textCase(nil)
    }
}

final class SearchProductModel: ObservableObject {
    @Published private(set) var productGroups: [ProductGroup]? = nil
    
    private var searchCancellable: Cancellable? {
        didSet { oldValue?.cancel() }
    }

    deinit {
        searchCancellable?.cancel()
    }
    
    func search(text: String) {
        var urlComponents = URLComponents(string: "http://127.0.0.1:8080/search")!
        urlComponents.queryItems = [
            URLQueryItem(name: "q", value: text)
        ]

        var request = URLRequest(url: urlComponents.url!)
        request.addValue("application/json", forHTTPHeaderField: "Content-Type")

        searchCancellable = URLSession.shared.dataTaskPublisher(for: request)
            .map { $0.data }
            .decode(type: [ProductGroup]?.self, decoder: JSONDecoder())
            .replaceError(with: nil)
            .receive(on: RunLoop.main)
            .assign(to: \.productGroups, on: self)

    }
}

struct ContentView: View {
    @ObservedObject var searchModel = SearchProductModel()
    @State var searchText = ""
    @State var isSearching = false
    
    var body: some View {
        NavigationView {
            VStack {
                SearchBarView(searchText: $searchText, isSearching: $isSearching, perform: searchModel.search)
                if self.isSearching {
                    if searchModel.productGroups == nil {
                        ErrorView(error: "Error occured on requesting")
                    } else if searchModel.productGroups! != [] {
                        List {
                            ForEach(searchModel.productGroups!, id: \.self) { group in
                                ResultSection(productGroup: group)
                            }
                        }
                        .listStyle(GroupedListStyle())
                    } else {
                        ErrorView(error: "No Result")
                    }
                } else {
                    Spacer()
                }
            }
            .navigationTitle("Search")
        }
        .onAppear(perform: initView)
    }
    
    func initView() {
        initWebServer()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

struct SearchBarView: View {
    @Binding var searchText: String
    @Binding var isSearching: Bool
    @State private var isEditing: Bool = false
    var perform: ((String) -> Void)? = nil
    var placeholder: String = "Tap here to search"
    
    var body: some View {
        HStack {
            HStack {
                Image(systemName: "magnifyingglass")
                
                // Search text field
                ZStack (alignment: .leading) {
                    if searchText.isEmpty { // Separate text for placeholder to give it the proper color
                        Text(placeholder)
                    }
                    TextField("", text: $searchText, onEditingChanged: { isEditing in
                        self.isEditing = true
                    }, onCommit: {
                        if self.perform != nil {
                            self.perform!(searchText)
                        }
                        self.isSearching = true
                    }).foregroundColor(.primary)
                }
                
                // Clear button
                Button(action: {
                    self.searchText = ""
                    self.isSearching = false
                }) {
                    Image(systemName: "xmark.circle.fill").opacity(searchText == "" ? 0 : 1)
                }
            }
            .padding(EdgeInsets(top: 8, leading: 6, bottom: 8, trailing: 6))
            .foregroundColor(.secondary) // For magnifying glass and placeholder test
            .background(Color(.tertiarySystemFill))
            .cornerRadius(10.0)
        }
        .padding(.horizontal)
        .navigationBarHidden(isEditing)
    }
}
