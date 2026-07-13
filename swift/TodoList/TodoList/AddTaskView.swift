//
//  addTaskView.swift
//  TodoList
//
//  Created by Huanrong on 11/2/21.
//

import SwiftUI

struct AddTaskView: View {
    var modelData: ModelData
    @State var taskName: String = ""
    @State var groupName: String = ""
    
    @State var alertErrorIsPresented = false
    @State var alertError: String = ""
    
    @State var currentGroup = "Selete Group"
    
    var body: some View {
        HStack{
            TextField("Add new...", text: $taskName).textFieldStyle(RoundedBorderTextFieldStyle()).padding(5).toolbar {
                ToolbarItemGroup(placement: .keyboard) {
                    Button("Done") {
                        //Make sure taskName is available
                        guard taskName.count > 0 else {
                            self.alertError = "Please input task name"
                            alertErrorIsPresented = true
                            return
                        }
                        
                        //Make sure group is seleted
                        guard currentGroup != "Selete Group" else {
                            self.alertError = "Please selete group"
                            alertErrorIsPresented = true
                            return
                        }
                        
                        modelData.addItem(section:currentGroup, item: ItemsModel(name: taskName, done: false))
                        
                        //Clear the content
                        taskName = ""
                        
                        //dismiss the keyboard.
                        UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
                    }
                }
            }
            Menu(currentGroup) {
                //Add new group
                Button("✳️Add new Group") {
                    
                }
                
                //Group you alredy created
                ForEach(modelData.sections) { section in
                    Button(section.sectionName) {
                        self.currentGroup = section.sectionName
                    }
                }
            }.padding(5)
        }.alert(isPresented: $alertErrorIsPresented) {
            Alert(title: Text("⚠️"), message: Text(alertError))
        }
    }
}

struct addTaskView_Previews: PreviewProvider {
    static var previews: some View {
        AddTaskView(modelData: ModelData())
    }
}
