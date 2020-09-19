import SwiftUI

struct RefreshFooter: View {
    @Binding var isRefreshing: Bool
    
    var body: some View {
        Group {
            if isRefreshing {
                Image(systemName: "arrow.clockwise")
            } else {
                VStack(spacing: 8) {
                    Text("Load more data.")
                    Image(systemName: "arrow.down")
                }
            }
        }
    }
}

struct RefreshFooter_Previews: PreviewProvider {
    static var previews: some View {
        RefreshFooter(isRefreshing: .constant(false))
    }
}

