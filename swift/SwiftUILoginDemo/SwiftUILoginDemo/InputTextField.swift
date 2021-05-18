import SwiftUI

enum TextFieldType: String {
    case name = "Name"
    case password = "Password"
    case repeatPassword = "Repeat Password"
}

struct InputTextField: View {
    @Binding var inputText: String

    var textFieldType: TextFieldType

    var body: some View {
        VStack {
            if textFieldType == .name {
                TextField(textFieldType.rawValue, text: $inputText)
                    .frame(width: UIScreen.main.bounds.width - 50, height: 30, alignment: .center)
                    .deleteDisabled(true)
            } else {
                SecureField(textFieldType.rawValue, text: $inputText)
                    .frame(width: UIScreen.main.bounds.width - 50, height: 30, alignment: .center)
            }

            Divider()
                .background(lightGreyColor)
                .frame(width: UIScreen.main.bounds.width - 30, height: 2, alignment: .center)
        }
    }
}
