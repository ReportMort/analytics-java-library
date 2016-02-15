package com.adobe.analytics.client.domain;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Report {

	@SerializedName("type")
	private String type;

	@SerializedName("reportSuite")
	private ReportReportSuite reportSuite;

	@SerializedName("period")
	private String period;

	@SerializedName("elements")
	private List<ReportElement> elements;

	@SerializedName("metrics")
	private List<ReportMetric> metrics;

	@SerializedName("segments")
	private List<ReportSegment> segments;

	@SerializedName("data")
	private List<ReportData> data;

	@SerializedName("totals")
	private List<Double> totals;

	@SerializedName("version")
	private String version;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ReportReportSuite getReportSuite() {
		return reportSuite;
	}

	public void setReportSuite(ReportReportSuite reportSuite) {
		this.reportSuite = reportSuite;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public List<ReportElement> getElements() {
		return elements;
	}

	public void setElements(List<ReportElement> elements) {
		this.elements = elements;
	}

	public List<ReportMetric> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<ReportMetric> metrics) {
		this.metrics = metrics;
	}

	public List<ReportSegment> getSegments() {
		return segments;
	}

	public void setSegments(List<ReportSegment> segments) {
		this.segments = segments;
	}

	public List<ReportData> getData() {
		return data;
	}

	public void setData(List<ReportData> data) {
		this.data = data;
	}

	public List<Double> getTotals() {
		return totals;
	}

	public void setTotals(List<Double> totals) {
		this.totals = totals;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	// copy from https://github.com/Adobe-Marketing-Cloud/analytics-samples/blob/master/export-report/java/src/main/java/com/adobe/analytics/sample/report/ExportReport.java
	public List<Record> flattenReportData(List<ReportData> dataList, Record partialRecord) {
		final List<Record> records = new ArrayList<>();
		for (final ReportData data : dataList) {
			final Record record = partialRecord.clone();
			record.addElements(data);
			if (data.getBreakdown() == null) {
				record.addMetrics(data);
				records.add(record);
			} else {
				records.addAll(flattenReportData(data.getBreakdown(), record));
			}
		}
		return records;
	}
	
	// copy from https://github.com/Adobe-Marketing-Cloud/analytics-samples/blob/master/export-report/java/src/main/java/com/adobe/analytics/sample/report/ExportReport.java
	public List<Record> getRecords() {
		List<ReportData> dataList = this.getData();
		Record partialRecord = new Record(this.getMetrics().size() + 1);
		List<Record> records = new ArrayList<>();
		records = flattenReportData(dataList, partialRecord);
		return records;
	}

	// copy from https://github.com/Adobe-Marketing-Cloud/analytics-samples/blob/master/export-report/java/src/main/java/com/adobe/analytics/sample/report/ExportReport.java
	public List<String> getHeaders() {
		List<String> headers = new ArrayList<>();
		headers.add("name");
		ReportData data = this.getData().get(0);
		if (data.getYear() != null) {
			headers.add("year");
		}
		if (data.getMonth() != null) {
			headers.add("month");
		}
		if (data.getDay() != null) {
			headers.add("day");
		}
		if (data.getHour() != null) {
			headers.add("hour");
		}
		if (data.getMinute() != null) {
			headers.add("minute");
		}
		for (final ReportElement e : this.getElements()) {
			headers.add(e.getId());
		}
		for (final ReportMetric m : this.getMetrics()) {
			headers.add(m.getId());
		}
		return headers;
	}
}
