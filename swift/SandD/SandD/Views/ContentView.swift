import SwiftUI

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

struct ContentView: View {
    @State private var searchText: String = ""
    @State private var searchError = false
    @State private var productGroups: [ProductGroup] = []
    
    var body: some View {
        NavigationView {
            VStack {
                if searchError {
                    ErrorView(error: "Error occured on requesting")
                } else if productGroups != [] {
                    List {
                        ForEach(self.productGroups, id: \.self) { group in
                            ResultSection(productGroup: group)
                        }
                    }
                    .listStyle(GroupedListStyle())
                } else if searchText != "" {
                    ErrorView(error: "No Result")
                }
            }
            .navigationTitle("Search")
            .overlay(NavigationSearch(text: $searchText, placeholder: "Tap here to search").frame(width: 0, height: 0))
        }
        .onAppear(perform: initView)
        .onChange(of: searchText, perform: { value in
            search(value: value)
        })
    }
    
    func initView() {
        initWebServer()
    }
    
    func search(value: String) {
        searchError = false
        
        let url = URL(string: "http://127.0.0.1:8080/search?q="+searchText)!

        let task = URLSession.shared.dataTask(with: url) {(data, response, error) in
            if error != nil {
                print("Network error: \(String(describing: error)).")
                searchError = true
                return
            }
            let data = data!
            print(String(data: data, encoding: .utf8)!)
            let decoder = JSONDecoder()
            do {
                productGroups = try decoder.decode([ProductGroup].self, from: data)
            } catch {
                print("Decode error: \(error).")
                searchError = true
            }
        }

        task.resume()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
