//
//  ToToItemView.swift
//  InterviewAssignment (iOS)
//
//  Created by Vic Zhang on 2021/12/10.
//

import SwiftUI
import Combine

struct ColorInvert: ViewModifier {

    @Environment(\.colorScheme) var colorScheme

    func body(content: Content) -> some View {
        Group {
            if colorScheme == .dark {
                content.colorInvert()
            } else {
                content
            }
        }
    }
}

struct ToDoItemView: View {
    @State var isEditing: Bool = false
    
    @FocusState private var focused : Bool
    
    @State var name: String = ""
    @State var categoryName: String = ""
    @State var finished: Bool = false
    private var id: Int = 0
    
    let onEdited: (Int, String, String, Bool) -> ()
    let onStateChanged: (Bool) -> ()
    
    init(item: ToDoItem, onEdited: @escaping (Int, String, String, Bool)->(), onStateChanged: @escaping (Bool) -> ()) {
        self.name = item.name
        self.categoryName = item.category
        self.id = item.id
        self.finished = item.finished
        self.onEdited = onEdited
        self.onStateChanged = onStateChanged
    }
    
    var body: some View {
        Button(action: {
            finished = !finished
            self.onEdited(id, categoryName, name, finished)
        }) {
            HStack {
                Image(systemName: finished ? "largecircle.fill.circle" : "circle")
                                            .renderingMode(.original)
                                            .resizable()
                                            .aspectRatio(contentMode: .fit)
                                            .frame(width: 20, height: 20)
                                            .modifier(ColorInvert())
                if (self.isEditing) {
                    TextField("", text: $name, prompt: nil).textFieldStyle(.roundedBorder).focused($focused).onSubmit {
                        self.isEditing = false
                        self.onEdited(id, categoryName, name, finished)
                    }.onAppear {
                        self.focused = true
                    }
                } else {
                    if (finished) {
                        Text(name).strikethrough().onLongPressGesture {
                            self.isEditing = true
                            self.onStateChanged(isEditing)
                        }
                    } else {
                        Text(name).onLongPressGesture {
                            self.isEditing = true
                            self.onStateChanged(isEditing)
                        }
                    }
                }
                Spacer()
            }
        }
    }
}

struct ToToItemView_Previews: PreviewProvider {
    static var previews: some View {
        ToDoItemView(item: ToDoItem(), onEdited: {id, category, name,finished in }, onStateChanged: {editing in })
    }
}
