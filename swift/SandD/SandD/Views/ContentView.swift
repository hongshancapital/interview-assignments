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
                SearchBarView(searchText: $searchText)
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
                } else {
                    Spacer()
                }
            }
            .navigationTitle("Search")
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

struct SearchBarView: View {
    @Binding var searchText: String
    @State private var isEditing: Bool = false
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
                          }).foregroundColor(.primary)
                }
                
                // Clear button
                Button(action: {
                    self.searchText = ""
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
