import SwiftUI

public struct SearchBar: View {
    
    public init(title: String = "", text: Binding<String>, isEditing: Binding<Bool>, onCommit: @escaping () -> Void = {}) {
        _title = title
        _text = text
        _isEditing = isEditing
        _onCommit = onCommit
    }
    
    private let _title: String
    
    @Binding
    private var text: String
    
    @Binding
    private var isEditing: Bool
    
    private let _onCommit: () -> Void
    
    public var body: some View {
        HStack {
            Image(systemName: "magnifyingglass")
            TextField(_title, text: _text, onEditingChanged: {
                isEditing = $0
            }, onCommit: _onCommit)
            if isEditing || !text.isEmpty {
                Button(action: clear) {
                    Image(systemName: "multiply")
                }
                .foregroundColor(.gray)
            }
        }
        .padding()
        .background(Color.gray.opacity(0.2).cornerRadius(15))
        .padding()
    }
    
    func clear() {
        if text.isEmpty {
            #if canImport(UIKit)
            UIApplication.shared.windows.first?.endEditing(false)
            #endif
        } else {
            text = ""
        }
    }
}

struct SearchBar_Previews: PreviewProvider {
    static var previews: some View {
        SearchBar(text: .constant("123"), isEditing: .constant(true))
    }
}
