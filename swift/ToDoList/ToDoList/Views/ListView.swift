//
//  ListView.swift
//  ToDoList
//
//  Created by 炎檬 on 2022/1/13.
//

import SwiftUI

let bottomHeight: CGFloat = 100

struct ListView: View {
    
    @State private var addItemContent: String = ""
    @State private var searchItemContent: String = ""
    
    @State private var isFocus: Bool = false
    
    @FocusState private var focusField: Bool
    
    @StateObject private var viewModel: ListViewViewModel = ListViewViewModel()
    
    @State private var editingItem: [String: ItemModel]?
    
    var body: some View {
        List {
            ForEach(searchItemContent.isEmpty ? viewModel.groups : viewModel.filterGroups, id: \.self) { group in
                Section(header: Text(group.groupTitle)) {
                    ForEach(group.items, id: \.self) { item in
                        ListRowView(itemModel: item)
                            .listRowSeparator(.hidden)
                            .listRowInsets(EdgeInsets(top: 10, leading: 10, bottom: 10, trailing: 10))
                            .listRowBackground(Color.clear)
                            .onTapGesture {
                                onCheckItem(group, item)
                            }
                            .onLongPressGesture {
                                onEditItem(group, item)
                            }
                    }
                }
                .allowsHitTesting(!isFocus)
                .foregroundColor(Color.black)
            }
        }
        .listStyle(GroupedListStyle())
        .navigationTitle("List")
        .padding(EdgeInsets(top: 0, leading: 0, bottom: bottomHeight, trailing: 0))
        .overlay(
            VStack {
                HStack {
                    TextField("Add new...", text: $addItemContent, onEditingChanged: onAddEditingChanged, onCommit: onCommit)
                        .focused($focusField)
                        .frame(height: 30)
                        .padding(EdgeInsets(top: 10, leading: 10, bottom: 10, trailing: 10))
                        .background(Color.white).cornerRadius(6)
                    if isFocus {
                        Menu {
                            Picker(selection: $viewModel.selectedGroup, label: EmptyView()) {
                                ForEach(viewModel.groups.map{ $0.groupTitle }, id: \.self) {
                                    Text($0)
                                }
                            }
                        } label: {
                            HStack {
                                Text(viewModel.selectedGroup)
                                    .frame(maxWidth: 50)
                                    .frame(height: 20, alignment: .leading )
                                    .lineLimit(2)
                                Image(systemName: "chevron.down").font(.system(size: 8)).foregroundColor(Color.gray)
                            }
                            .foregroundColor(Color.black)
                            .font(.system(size: 8))
                            .frame(height: 30)
                            .frame(maxWidth: 80)
                            .background(Color.white)
                            .cornerRadius(15)
                        }.onTapGesture { print("##########") } //拦截点击事件，使picker能响应
                    }
                }
                .padding(EdgeInsets(top: 20, leading: 20, bottom: 10, trailing: 20))
            }
                .background(Color.init(hex: 0xf7f7f8).opacity(0.9))
                .frame(maxWidth: .infinity, maxHeight: bottomHeight, alignment: .bottom),
            alignment: .bottomTrailing
        )
        .onTapGesture {
            if isFocus {
                hideKeyBoard()
            }
        }
        .navigationBarItems(
            leading: TextField("Search", text: $searchItemContent, onEditingChanged: onSearchEditingChanged)
                .padding(EdgeInsets(top: 10, leading: 10, bottom: 10, trailing: 10))
                .frame(width: 250, height: 30)
                .onChange(of: searchItemContent, perform: { value in
                    viewModel.onSearchingChanged(value)
                })
                .background(Color.white)
                .cornerRadius(8),
            trailing: NavigationLink("Add Group", destination: AddNewGroupView(viewModel: viewModel))
        )
    }
    
    func onEditItem(_ group: GroupModel, _ item: ItemModel) {
        print("onEditItem: \(group.groupTitle)")
        print("onEditItem: \(item.title)")
        focusField = true
        viewModel.selectedGroup = group.groupTitle
        addItemContent = item.title
        editingItem = [group.groupTitle: item]
    }
    
    func onCheckItem(_ group: GroupModel, _ item: ItemModel) {
        print("onCheckItem: \(group.groupTitle)")
        print("onCheckItem: \(item.title)")
        viewModel.checkItem(group, item)
    }
    
    func onAddEditingChanged(focus: Bool) {
        print("onEditingChanged: \(focus)")
        isFocus = focus
    }
    
    func onSearchEditingChanged(focus: Bool) {
        print("onEditingChanged: \(focus)")
        isFocus = focus
    }
    
    func onCommit() {
        print("onCommit")
        var isChecked = false
        if let editingItem = editingItem, let group = editingItem.keys.first, let item = editingItem.values.first {
            viewModel.removeItem(group, item.title)
            isChecked = item.isChecked
        }
        viewModel.addItem(addItemContent, isChecked)
        addItemContent = ""
        editingItem = nil
    }
    
    func delete(_ group: GroupModel, _ item: ItemModel) {
        print("delete")
        viewModel.removeItem(group.groupTitle, item.title)
    }
}

extension View {
    @ViewBuilder
    func `if`<TrueContent: View, FalseContent: View>(
        _ condition: Bool,
        if ifTransform: (Self) -> TrueContent,
        else elseTransform: (Self) -> FalseContent
    ) -> some View {
        if condition {
            ifTransform(self)
        } else {
            elseTransform(self)
        }
    }
    
    func hideKeyBoard() {
        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
    }
}

extension Color {
    init(hex: Int, opacity: Double = 1.0) {
        let red = Double((hex & 0xff0000) >> 16) / 255.0
        let green = Double((hex & 0xff00) >> 8) / 255.0
        let blue = Double((hex & 0xff) >> 0) / 255.0
        self.init(.sRGB, red: red, green: green, blue: blue, opacity: opacity)
    }
}

struct ListView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            ListView()
        }
    }
}
