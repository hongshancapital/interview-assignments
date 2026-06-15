//
//  CategoryView.swift
//  todoList
//
//  Created by 邓志武(平安科技云技术服务团队咨询服务组) on 2021/12/7.
//

import SwiftUI

struct CategoryView: View {
    @EnvironmentObject var listViewModel: ListViewModel
    @State var textStr: String = ""
    @State var animate: Bool = false
    @State var showAlert: Bool = false

    var body: some View {
        VStack {
            Text("Add category, then select category")
                .font(.title2)
                .bold()
                .padding(.horizontal)
            

            List {
                ForEach(listViewModel.sectionItems) { item in
                    HStack(alignment: .center, spacing: 10) {
                        Image(systemName: item.isChecked ? "checkmark.circle" : "circle")
                        Text(item.header)
                    }
                    .onTapGesture {
                        listViewModel.changeCheck(item: item)
                    }
                }
                .onDelete(perform: listViewModel.deleteItem)
                .onMove(perform: listViewModel.moveItem)

            }.padding()
                .listStyle(PlainListStyle())
                .onAppear {
                    UITableView.appearance().separatorStyle = .none
                }

            Spacer()
            VStack {
                TextField("Please input category", text: self.$textStr, onCommit: {
                    if textStr.count < 1 {
                        self.showAlert = true
                    }
                    textStr = ""
                    UIApplication.shared.windows.first { $0.isKeyWindow }?.endEditing(true)

                })
                .frame(maxWidth: .infinity)
                .frame(height: 30)
                .padding(.horizontal, 30)

                Button(action: {
                    if textStr.count > 0 {
                        listViewModel.addItem(header: textStr)
                    } else {
                        self.showAlert = true
                    }
                    self.textStr = ""
                    UIApplication.shared.windows.first { $0.isKeyWindow }?.endEditing(true)

                }) {
                    Text("Add  Category")
                        .foregroundColor(.white)
                        .font(.subheadline)
                        .frame(maxWidth: .infinity)
                        .frame(height: 40)
                        .background(animate ? Color.red : Color.accentColor)
                        .cornerRadius(10)
                        .padding(.horizontal, animate ? 30 : 50)
                        .shadow(color: animate ? Color.red.opacity(0.6) : Color.accentColor.opacity(0.6),
                                radius: animate ? 20 : 10,
                                x: 0,
                                y: animate ? 30 : 10)
                        .scaleEffect(animate ? 1.1 : 1.0)
                        .offset(y: animate ? -5 : 0)
                }
                .padding()
                .onAppear(perform: addAnimation)
            }
            .alert(isPresented: $showAlert, content: {
                Alert(title: Text("empty"), message: Text("please input category"), dismissButton: .default(Text("ok")))
            })
        }
        .background(Color.white)
        .navigationBarItems(trailing: EditButton())
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

struct CategoryView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            CategoryView()
        }
        .environmentObject(ListViewModel())
    }
}
