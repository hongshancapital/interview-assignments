//
//  ContentView.swift
//  Shared
//
//  Created by Vic Zhang on 2021/12/10.
//

import SwiftUI
import Combine

struct ContentView: View {
    @ObservedObject var viewModel: ToDoListViewModel
    @State var newItemName: String = ""
    @State var newItemCategory: String = ""
    
    private let SearchPromtText = ""
    
    var body: some View {
        ZStack(alignment: .bottom) {
            NavigationView {
                List {
                    if (viewModel.state != .loading && viewModel.state != .noData) {
                        ForEach(viewModel.toDoItems.keys.sorted(), id: \.self) { key in
                            Section(key) {
                                ForEach(viewModel.toDoItems[key] ?? [ToDoItem]()) { toDoItem in
                                    ToDoItemView(item: toDoItem, onEdited: viewModel.modifyOrDelete(id:categoryName:name:finished:)) {editing in
                                        self.viewModel.state = editing ? .editing : .loaded
                                    }
                                }
                            }
                        }
                    } else {
                        Text(viewModel.state.rawValue)
                            .padding(.vertical)
                            .foregroundColor(.gray)
                            .background(.clear)
                            .multilineTextAlignment(.center)
                    }
                }.navigationBarTitle(Text("List"))
                    .searchable(text: $viewModel.searchText, placement: SearchFieldPlacement.automatic, prompt: Text(SearchPromtText))
                    .onChange(of: viewModel.searchText, perform: {newValue in
                        viewModel.search(by: newValue)
                    })
                    .onAppear(perform: {viewModel.loadAll()})
            }
            if (viewModel.state == .addingNew) {
                HStack(alignment: .top, spacing: 5) {
                    TextField("title", text: $newItemName, prompt: Text("Add new..."))
                        .background(.clear)
                        .foregroundColor(.black)
                        .textFieldStyle(.roundedBorder)
                        .keyboardType(UIKeyboardType.alphabet)
                        .frame(maxHeight: .infinity, alignment: .top)
                        .onSubmit {
                            self.viewModel.add(category: newItemCategory, name: newItemName)
                            self.newItemName = ""
                            self.newItemCategory = ""
                            viewModel.state = .loaded
                        }
                    
                    Picker(selection: $newItemCategory, label: Text("Category")) {
                        ForEach(viewModel.categories, id:\.self) {category in
                            Text(category).tag(category)
                        }
                    }.background(.clear)
                        .foregroundColor(.black)
                        .onAppear() {
                            self.newItemCategory = viewModel.categories.first ?? ""
                        }
                        .frame(maxHeight: .infinity)
                }.background(.clear)
                    .offset(x: 0, y: 20)
                    .fixedSize(horizontal: false, vertical: true)
                    .frame(maxHeight: 80)
            } else if (viewModel.state != .editing) {
                Button.init(action: {
                    self.viewModel.state = .addingNew
                }) {
                    Text("Add New")
                                .font(.system(size: 17))
                                .fontWeight(.regular)
                                .foregroundColor(.black)
                                .padding(EdgeInsets(top: 10, leading: 80, bottom: 10, trailing: 80))
                                .background(Color.gray)
                                .clipped()
                                .cornerRadius(20)
                }.offset(x: 0, y: -40)
                    .opacity(0.8)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(viewModel: ToDoListViewModel())
    }
}
