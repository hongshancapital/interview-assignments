//
//  ListView.swift
//  TodoList_SwiftUI
//
//  Created by zengmuqiang on 2021/11/23.
//

import SwiftUI

struct ListView: View {
    
    @EnvironmentObject var listViewModel: ListViewModel
    @State var textfieldText: String = ""
    @State private var inputStr: String = ""
    @State private var searchStr: String = ""
    @State private var alertMessage: String = ""
    @State var animate: Bool = false
    @State var showAlert: Bool = false
    @State var showEditAlert: Bool = false
    @State var hasEdit: Bool = false
    
    @State var isEditMode: EditMode = .inactive
    
    var body: some View {
        ZStack {
            if listViewModel.sectionItems.isEmpty {
                
                NoItemsView()
                    .transition(AnyTransition.opacity
                    .animation(.easeIn))
                
            } else {
                VStack {
                    SearchBarView(searchText: $searchStr)
                        .padding()
                    List {
                        ForEach(self.listViewModel.sectionItems.filter { sectionItem in

                            self.searchStr.isEmpty ? true : sectionItem.header.contains(self.searchStr) || (sectionItem.items.first(where: { $0.title.contains(self.searchStr)}) != nil)

                        }) { sectionItem in
                            
                            Section(header: HStack {
                                Text(sectionItem.header)
                                    .padding(.leading, 2)
                                    .padding(.top, 5)
                            }.background(Color.clear)) {

                                ForEach(sectionItem.items.filter { childItem in

                                    self.searchStr.isEmpty ? true : childItem.title.contains(self.searchStr)

                                }) { childItem in

                                    ListRowView(section: sectionItem, item: childItem)
                                        .onTapGesture {
                                            withAnimation(.linear) {
                                                if childItem.isEditing == true { return }
                                                childItem.isCompleted = !childItem.isCompleted
                                                listViewModel.updateChildItem(sectionItem: sectionItem, item: childItem)
                                            }
                                        }
                                        .onLongPressGesture(minimumDuration: 0.5) {
                                            if self.hasEdit && !childItem.isEditing {
                                                self.showEditAlert = true
                                                return
                                            }
                                            
                                            childItem.isEditing = !childItem.isEditing
                                            self.hasEdit = childItem.isEditing
                                            if self.hasEdit {
                                                isEditMode = .active
                                            } else {
                                                isEditMode = .inactive
                                            }
                                            listViewModel.updateChildItem(sectionItem: sectionItem, item: childItem)
                                        }.alert(isPresented: $showEditAlert, content: {
                                            Alert(title: Text("has todo editing"), message: Text("Please complete the todo list you are editing,you should long press or delete editing item to cancle edit state"), dismissButton: .default(Text("OK")))
                                        })
                                }
                                .onDelete { index in
                                    guard index.first != nil else { return }
                                    listViewModel.deleteChildItem(section: sectionItem, item: sectionItem.items[index.first!])
                                }
                                .onMove { from, to in
                                    listViewModel.moveChildItem(section: sectionItem, from: from, to: to)
                                }
                            }
                        }
                    }
                    .listStyle(SidebarListStyle())
                    Spacer()
                    VStack {
                        if let data = listViewModel.getCheckItem() {
                            NavigationLink(destination: HeaderView()) {
                                HStack {
                                    Text("CurrentHeader:")
                                        .foregroundColor(Color.accentColor)
                                        .padding(4)
                                    Text(data.header)
                                        .foregroundColor(Color.accentColor)
                                        .padding(4)
                                }
                                .frame(height: 35, alignment: .leading)
                                .cornerRadius(2.0)
                                .background(Color(UIColor.secondarySystemBackground))
                                .padding(.horizontal)
                            }
                            
                        } else {
                            NavigationLink(destination: HeaderView()) {
                                HStack {
                                    Text("Please select header")
                                        .foregroundColor(Color.accentColor)
                                        .padding(4)
                                    Image(systemName: "plus.circle")
                                        .padding(4)
                                        .foregroundColor(Color.accentColor)
                                        .scaleEffect(animate ? 1.2 : 1.0)
                                        .onAppear(perform: addAnimation)
                                }
                                .frame(height: 35, alignment: .leading)
                                .cornerRadius(2.0)
                                .background(Color(UIColor.secondarySystemBackground))
                                .padding(.horizontal)
                            }
                        }
                        
                        TextField("  You can add cell Item", text: $inputStr, onCommit: {
                            let data = listViewModel.getCheckItem()
                            if inputStr.count > 0 && data?.header != nil {
                                listViewModel.saveChildItem(value: inputStr)
                            } else if (inputStr.count > 0) {
                                alertMessage = "Please select header before add cell item."
                                self.showAlert = true
                            } else if (data?.header != nil) {
                                alertMessage = "Please input cell item."
                                self.showAlert = true
                            } else {
                                alertMessage = "Please select header before add cell item."
                                self.showAlert = true
                            }
                            inputStr = ""
                            UIApplication.shared.windows.first { $0.isKeyWindow }?.endEditing(true)
                        })
                        .frame(maxWidth: .infinity)
                        .frame(height: 45)
                        .background(Color(UIColor.secondarySystemBackground))
                        .padding(.horizontal, 15)
                        .cornerRadius(10)
                        .alert(isPresented: $showAlert, content: {
                            Alert(title: Text("🤪"), message: Text(alertMessage), dismissButton: .default(Text("OK")))
                        })
                    }
                }
            }
        }
        .toolbar {
            Button(isEditMode.isEditing ? "完成" : "编辑") {
                switch isEditMode {
                case .active:
                    self.isEditMode = .inactive
                case .inactive:
                    self.isEditMode = .active
                default:
                    break
                }
            }
        }
        .environment(\.editMode, $isEditMode)
        .navigationBarTitle(Text("TodoList ✍🏻"))
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

struct ListView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            ListView()
        }
        .environmentObject(ListViewModel())
    }
}



