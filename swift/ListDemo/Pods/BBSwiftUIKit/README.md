# BBSwiftUIKit

A SwiftUI library with powerful UIKit features.

## Features

- [x] Get and set scroll view `contentOffset`
- [x] Set scroll view `isPagingEnabled`, `bounces` and other properties
- [x] Pull down or up table view to refresh data or load more data
- [x] Reload table view data or rows
- [x] Scroll table view to the specific row
- [x] Get and set page control `currentPage`

## Requirements

- iOS 13.0+
- Swift 5

## Installation

Install with CocoaPods:

1. Add `pod 'BBSwiftUIKit'` to your Podfile.
2. Run `pod install` or `pod update`.
3. Add `import BBSwiftUIKit` to the Swift source file.

##  How to Use

### Scroll View

Use `BBScrollView` with `UIScrollView` features.

```swift
// Content offset binding with scroll view content offset
@State var contentOffset: CGPoint = .zero

// Set CGPoint value to scroll to the content offset with animation. After scrolling, it will reset to nil automatically
@State var contentOffsetToScrollAnimated: CGPoint? = nil

// Create a scroll view scrolling horizontally
// Get and set content offset
BBScrollView(.horizontal, contentOffset: $contentOffset) {
    // Add views
    HStack {
        ...
    }
}
.bb_bounces(false) // Disable bounces
.bb_isPagingEnabled(true) // Enable paging
.bb_alwaysBounceHorizontal(true) // Enable always bounce horizontal
.bb_showsHorizontalScrollIndicator(false) // Hide horizontal scroll indicator
.bb_contentOffsetToScrollAnimated($contentOffsetToScrollAnimated) // Set content offset with animation
```

### Table View

Use `BBTableView` with `UITableView` features.

```swift
class Model: ObservableObject {
    // Table view data source
    @Published var list: [Int] = ...
    
    // Set true to reload data. After reloading, it will reset to false automatically
    @Published var reloadData: Bool = false
    
    // Set row indexes to reload rows. After reloading, it will reset to empty automatically
    @Published var reloadRows: [Int] = []
    
    // Set row index to scroll to the specific row. After scrolling, it will reset to nil automatically
    @Published var scrollToRow: Int? = nil

    // Pull down to refresh data. While refreshing, this value is true. After refreshing, we need to set it false to end refreshing
    @Published var isRefreshing: Bool = false

    // Pull up to loading more data. We should manage this value to prevent calling load more function too many times
    @Published var isLoadingMore: Bool = false
}

// Create a table view with data source
BBTableView(model.list) { item in
    // Add views
}
.bb_reloadData($model.reloadData) // Reload data
.bb_reloadRows($model.reloadRows, animation: .automatic) // Reload rows
.bb_scrollToRow($model.scrollToRow, position: .none, animated: true) // Scroll to row
.bb_setupRefreshControl { refreshControl in
    // Update refresh control appearance
    refreshControl.tintColor = .blue
    refreshControl.attributedTitle = NSAttributedString(string: "Loading...", attributes: [.font: UIFont.systemFont(ofSize: 15), .foregroundColor: UIColor.blue])
}
.bb_pullDownToRefresh(isRefreshing: $model.isRefreshing) {
    // Pull down to refresh data
    // Now the value of isRefreshing is true
    self.loadNewData { newData in
        self.model.list = newData // Update data source
        self.model.isRefreshing = false // End refreshing
    }
}
.bb_pullUpToLoadMore(bottomSpace: 30) {
    // Pull up to load more data
    if self.model.isLoadingMore { return } // Do not load more if it is loading more
    self.model.isLoadingMore = true // Mark it is loading more
    self.loadMoreData { moreData in
        self.model.list.append(moreData) // Update data source
        self.model.isLoadingMore = false // Mark it is NOT loading more
    }
}
```

## License

BBSwiftUIKit is released under the MIT license. See [LICENSE](LICENSE) for details.