//
//  ListView.swift
//  todoList
//
//  Created by deng on 2021/12/6.
//

import SwiftUI

extension UIColor {
    convenience init(red: Int, green: Int, blue: Int) {
        assert(red >= 0 && red <= 255, "Invalid red component")
        assert(green >= 0 && green <= 255, "Invalid green component")
        assert(blue >= 0 && blue <= 255, "Invalid blue component")

        self.init(red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0, blue: CGFloat(blue) / 255.0, alpha: 1.0)
    }

    convenience init(rgb: Int) {
        self.init(
            red: (rgb >> 16) & 0xFF,
            green: (rgb >> 8) & 0xFF,
            blue: rgb & 0xFF
        )
    }
}

struct ListView: View {
    @EnvironmentObject var listViewModel: ListViewModel
    @State private var inputMessage = ""
    @State private var searchInfo: String = ""
    @State var animate: Bool = false
    @State var hasCategory: Bool = false
    @State var showAlert: Bool = false
    @State var showEditAlert: Bool = false
    @State var hasEdit: Bool = false

    init() {
        let backgroundColor = UIColor(rgb: 0xEEEEEE)
        let titleColor = UIColor(rgb: 0x000000)
        let coloredAppearance = UINavigationBarAppearance()
        coloredAppearance.configureWithTransparentBackground()
        coloredAppearance.backgroundColor = backgroundColor
        coloredAppearance.titleTextAttributes = [.foregroundColor: titleColor]
        coloredAppearance.largeTitleTextAttributes = [.foregroundColor: titleColor]
        UINavigationBar.appearance().standardAppearance = coloredAppearance
        UINavigationBar.appearance().compactAppearance = coloredAppearance
        UINavigationBar.appearance().scrollEdgeAppearance = coloredAppearance
    }

    var body: some View {
        VStack {
            ScrollView(.vertical) {
                SearchBar(searchText: $searchInfo)
                ForEach(self.listViewModel.sectionItems.filter { msection in self.searchInfo.isEmpty ? true : msection.header.contains(self.searchInfo) || (msection.items.first(where: { $0.title.contains(self.searchInfo) }) != nil)
                }) { item in
                    Section(header: HStack {
                        Text(item.header)
                            .padding(.leading, 2)
                        Spacer()
                    }.background(Color.clear)

                    ) {
                        ForEach(item.items.filter { mitem in
                            self.searchInfo.isEmpty ? true : mitem.title.contains(self.searchInfo)
                        }) { subItem in
                            ListRowView(section: item, item: subItem)
                                .padding(4)
                                .onTapGesture {
                                    if subItem.isEditing == true {
                                        return
                                    }
                                    subItem.isCompeleted = !subItem.isCompeleted
                                    listViewModel.updateSubItem(sectionItem: item, item: subItem)
                                }
                                .onLongPressGesture(minimumDuration: 1.5) {
                                    if self.hasEdit && !subItem.isEditing {
                                        self.showEditAlert = true
                                        return
                                    }
                                    subItem.isEditing = !subItem.isEditing
                                    self.hasEdit = subItem.isEditing
                                    listViewModel.updateSubItem(sectionItem: item, item: subItem)
                                }.alert(isPresented: $showEditAlert, content: {
                                    Alert(title: Text("has todo editing"), message: Text("Please complete the todo list you are editing,you should long press or delete editing item to cancle edit state"), dismissButton: .default(Text("ok")))
                                })
                        }
                    }
                }
            }
            .padding()
            

            Spacer()
            HStack {
                TextField("input todo", text: $inputMessage, onCommit: {
                    if inputMessage.count > 0 {
                        listViewModel.saveSubItem(value: inputMessage)
                    } else {
                        self.showAlert = true
                    }
                    inputMessage = ""
                    UIApplication.shared.windows.first { $0.isKeyWindow }?.endEditing(true)

                })
                .alert(isPresented: $showAlert, content: {
                    Alert(title: Text("empty"), message: Text("please input todo"), dismissButton: .default(Text("ok")))
                })
                .frame(height: 30, alignment: .leading)
                .background(Color.white)
                .overlay(
                    RoundedRectangle(cornerRadius: 5, style: .circular)
                        .stroke(Color.gray.opacity(0.5), lineWidth: 1)
                        .padding()
                )
                .cornerRadius(4.0)

                if let data = listViewModel.getCheckItem() {
                    NavigationLink(destination: CategoryView()) {
                        HStack {
                            Text(data.header)
                                .foregroundColor(.gray)
                                .padding(4)

                            Image(systemName: "plus.circle")
                                .foregroundColor(Color.red)
                                .scaleEffect(animate ? 1.2 : 1.0)
                                .onAppear(perform: addAnimation)
                        }
                        .background(Color.white)
                        .cornerRadius(10.0)
                        .padding(4)
                    }

                } else {
                    NavigationLink(destination: CategoryView()) {
                        HStack {
                            Text("Please select category")
                                .foregroundColor(.gray)
                                .padding(4)
                            Image(systemName: "plus.circle")
                                .foregroundColor(Color.red)
                                .scaleEffect(animate ? 1.2 : 1.0)
                                .onAppear(perform: addAnimation)
                        }
                        .background(Color.white)
                        .cornerRadius(10.0)
                        .padding(4)
                    }
                }

            }
            .padding(.horizontal)
            Spacer()
        }
        .background(Color.gray.opacity(0.2))
        .navigationBarTitle(Text("List"))
    }

    func addAnimation() {
        guard !animate else { return }
        DispatchQueue.main.asyncAfter(deadline: .now() + 1.5) {
            withAnimation(
                Animation
                    .easeInOut(duration: 2.0)
                    .repeatForever()
            ) {
                animate.toggle()
            }
        }
    }
}

struct SearchBar: View {
    @Binding var searchText: String

    var body: some View {
        HStack {
            Image(systemName: "magnifyingglass")
                .padding(.leading, 5)
                .foregroundColor(.secondary)
            TextField("Search", text: $searchText, onCommit: {
                UIApplication.shared.windows.first { $0.isKeyWindow }?.endEditing(true)
            })
            Button(action: {
                searchText = ""
            }) {
                Image(systemName: "xmark.circle.fill")
                    .foregroundColor(.secondary)
                    .opacity(searchText == "" ? 0 : 1)
            }.padding(.horizontal)
        }
        .frame(height: 30, alignment: .leading)
        .background(Color.white)
        .cornerRadius(4)
        .overlay(
            RoundedRectangle(cornerRadius: 5, style: .circular)
                .stroke(Color.white, lineWidth: 1)
        )
    }
}

struct ListView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            ListView()
        }
        .environmentObject(ListViewModel())
    }
}
